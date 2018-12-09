package com.avalonomnimedia.playingcardsengine

import io.mockk.mockk
import org.junit.Assert
import org.junit.Test

class TestGameRunner {

    private val gameContext = mockk<GameContextBase>()

    @Test
    fun `when runner is created, should call init method`() {
        var actual = false

        val initialState = object : IGamePhase { }
        createRunner(gameContext, initialState) {
           actual = true
        }

        Assert.assertTrue("`init` not called", actual)
    }

    @Test
    fun `when runner is created, should have supplied GameContextBase`() {
        val expected = mockk<GameContextBase>()

        val initialState = object : IGamePhase { }
        val uut = createRunner(expected, initialState) { }

        val actual = uut.gameContext

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `when runner is created, should have supplied initial phase`() {
        val expected = object : IGamePhase { }

        val uut = createRunner(gameContext, expected) { }

        val actual = uut.currentPhase

        Assert.assertEquals(expected, actual)
    }

    class DummyPhase : IGamePhase

    @Test
    fun `when phase is created, should be added to phase list`() {
        val expected = DummyPhase::class
        val initialState = object : IGamePhase { }

        val uut = createRunner(gameContext, initialState) {
            phase<DummyPhase> {  }
        }

        val actual = uut.phaseDefinitions.single().type

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `when phase is created, should call init`() {
        var actual = false
        val initialState = DummyPhase()

        createRunner(gameContext, initialState) {
            phase<DummyPhase> { actual = true }
        }

        Assert.assertTrue("`init` on phase not called", actual)
    }

    class InitialPhase : IGamePhase
    class NextPhase : IGamePhase
    class DummyAction : IGameAction

    @Test
    fun `when action is performed, should fire exit actions on initial phase`() {
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

        Assert.assertTrue("`onExit` on phase not called", actual)
    }

    @Test
    fun `when action is performed that has not been registered, should throw InvalidActionException`() {
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

        Assert.assertTrue("`onExit` on phase not called", actual)
    }

    @Test
    fun `when action is performed that has been registered, should perform that transition`() {
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

        Assert.assertTrue("Transition lambda not called", actual)
    }

    @Test
    fun `when action is performed that calls phase that has not been defined, should throw PhaseNotDefinedException`() {
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

        Assert.assertTrue("Phase _was_ defined!", actual)
    }

    @Test
    fun `when action is performed, should fire enter actions on next phase`() {
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

        Assert.assertTrue("`onEntry` wasn't defined", actual)
    }

    @Test
    fun `when action is performed, currentPhase should change to phased returned from transition`() {
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

        Assert.assertEquals(expected, actual)
    }
}