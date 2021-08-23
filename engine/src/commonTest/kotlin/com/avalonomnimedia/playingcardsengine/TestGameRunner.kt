package com.avalonomnimedia.playingcardsengine

import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestGameRunner {
    private val gameContext = mockk<GameContextBase>()

    @Test
    fun whenRunnerIsCreated_ShouldCallInitMethod() {
        var actual = false

        val initialState = object : IGamePhase { }
        createRunner(gameContext, initialState) {
           actual = true
        }

        assertTrue(actual, "`init` not called")
    }

    @Test
    fun whenRunnerIsCreated_ShouldHaveSuppliedGameContextBase() {
        val expected = mockk<GameContextBase>()

        val initialState = object : IGamePhase { }
        val uut = createRunner(expected, initialState) { }

        val actual = uut.gameContext

        assertEquals(expected, actual)
    }

    @Test
    fun whenRunnerIsCreated_ShouldHaveSuppliedInitialPhase() {
        val expected = object : IGamePhase { }

        val uut = createRunner(gameContext, expected) { }

        val actual = uut.currentPhase

        assertEquals(expected, actual)
    }

    class DummyPhase : IGamePhase

    @Test
    fun whenPhaseIsCreated_ShouldBeAddedToPhaseList() {
        val expected = DummyPhase::class
        val initialState = object : IGamePhase { }

        val uut = createRunner(gameContext, initialState) {
            phase<DummyPhase> {  }
        }

        val actual = uut.phaseDefinitions.single().type

        assertEquals(expected, actual)
    }

    @Test
    fun whenPhaseIsCreated_ShouldCallInit() {
        var actual = false
        val initialState = DummyPhase()

        createRunner(gameContext, initialState) {
            phase<DummyPhase> { actual = true }
        }

        assertTrue(actual, "`init` on phase not called")
    }

    class InitialPhase : IGamePhase
    class NextPhase : IGamePhase
    class DummyAction : IGameAction

    @Test
    fun whenActionIsPerformed_ShouldFireExitActionsOnInitialPhase() {
        var actual = false
        val initialState = InitialPhase()

        val uut = createRunner(gameContext, initialState) {
            phase<InitialPhase> {
                onExit { actual = true }
            }
        }

        try {

            uut.perform(DummyAction())
        } catch (_: ActionNotRegisteredException) {
            // We haven't registered an action, so this will throw after calling exit actions.
            // For this particular test, we don't care.
        }

        assertTrue(actual, "`onExit` on phase not called")
    }

    @Test
    fun whenActionIsPerformedThatHasNotBeenRegistered_ShouldThrowInvalidActionException() {
        var actual = false
        val initialState = InitialPhase()

        val uut = createRunner(gameContext, initialState) {
            phase<InitialPhase> {}
        }

        try {
            uut.perform(DummyAction())
        } catch (_: ActionNotRegisteredException) {
            actual = true
        }

        assertTrue(actual, "`onExit` on phase not called")
    }

    @Test
    fun whenActionIsPerformedThatHasBeenRegistered_ShouldPerformThatTransition() {
        var actual = false
        val initialState = InitialPhase()

        val uut = createRunner(gameContext, initialState) {
            phase<InitialPhase> {
                on<DummyAction> {
                    actual = true
                    NextPhase::class
                }
            }
        }

        try {
            uut.perform(DummyAction())
        } catch (_: PhaseNotDefinedException) {
            // We haven't registered an additional phase, so this will throw after performing transition.
            // For this particular test, we don't care.
        }

        assertTrue(actual, "Transition lambda not called")
    }

    @Test
    fun whenActionIsPerformedThatCallsPhaseThatHasNotBeenDefined_ShouldThrowPhaseNotDefinedException() {
        var actual = false
        val initialState = InitialPhase()

        val uut = createRunner(gameContext, initialState) {
            phase<InitialPhase> {
                on<DummyAction> {
                    NextPhase::class
                }
            }
        }

        try {
            uut.perform(DummyAction())
        } catch (_: PhaseNotDefinedException) {
            actual = true
        }

        assertTrue(actual, "Phase _was_ defined!")
    }

    @Test
    fun whenActionIsPerformed_ShouldFireEnterActionsOnNextPhase() {
        var actual = false
        val initialState = InitialPhase()

        val uut = createRunner(gameContext, initialState) {
            phase<InitialPhase> {
                on<DummyAction> {
                    NextPhase::class
                }
            }
            phase<NextPhase> {
                onEntry {
                    actual = true
                }
            }
        }

        uut.perform(DummyAction())

        assertTrue(actual, "`onEntry` wasn't defined")
    }

    @Test
    fun whenActionIsPerformed_CurrentPhaseShouldChangeToPhasedReturnedFromTransition() {
        val expected = NextPhase::class
        val initialState = InitialPhase()

        val uut = createRunner(gameContext, initialState) {
            phase<InitialPhase> {
                on<DummyAction> {
                    NextPhase::class
                }
            }
            phase<NextPhase> {}
        }

        uut.perform(DummyAction())
        val actual = uut.currentPhase::class

        assertEquals(expected, actual)
    }
}