package com.avalonomnimedia.playingcardsengine

import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class TestPhaseDefinition {
    class DummyPhase : IGamePhase
    class DummyAction : IGameAction

    @Test
    fun whenOnIsCalled_ShouldStoreTransitionWithActionAsKey() {
        val uut = PhaseDefinition<DummyPhase, GameContextBase>(DummyPhase::class)
        uut.on(DummyAction::class) {
            DummyPhase::class
        }

        val actual = uut.transitions[DummyAction::class]

        assertNotNull(actual)
    }

    @Test
    fun whenInlineOnIsCalled_ShouldStoreTransitionWithActionAsKey() {
        val uut = PhaseDefinition<DummyPhase, GameContextBase>(DummyPhase::class)
        uut.on<DummyAction> {
            DummyPhase::class
        }

        val actual = uut.transitions[DummyAction::class]

        assertNotNull(actual)
    }

    @Test
    fun whenTransitionToIsCalled_ShouldReturnGenericAsKClass() {
        val expected = DummyPhase::class
        val uut = PhaseDefinition<DummyPhase, GameContextBase>(DummyPhase::class)

        val actual = uut.transitionTo<DummyPhase>()

        assertEquals(expected, actual)
    }

    @Test
    fun whenOnEntryIsCalled_ShouldStoreLambdaAsEntryAction() {
        val expected = fun(_: GameContextBase) = Unit
        val uut = PhaseDefinition<DummyPhase, GameContextBase>(DummyPhase::class)
        uut.onEntry(expected)

        val actual = uut.entryActions.single()

        assertEquals(expected, actual)
    }

    @Test
    fun whenEnterIsCalled_ShouldCallEntryAction() {
        var actual = false
        val action = fun(_: GameContextBase) {
            actual = true
        }
        val uut = PhaseDefinition<DummyPhase, GameContextBase>(DummyPhase::class)
        uut.entryActions.add(action)

        uut.enter(mockk())

        assertTrue(actual)
    }

    @Test
    fun whenEnterIsCalledAndHasMultipleEntryActions_AllShouldBeCalled() {
        var actual1 = false
        val action1 = fun(_: GameContextBase) {
            actual1 = true
        }
        var actual2 = false
        val action2 = fun(_: GameContextBase) {
            actual2 = true
        }
        var actual3 = false
        val action3 = fun(_: GameContextBase) {
            actual3 = true
        }
        val uut = PhaseDefinition<DummyPhase, GameContextBase>(DummyPhase::class)
        uut.entryActions.add(action1)
        uut.entryActions.add(action2)
        uut.entryActions.add(action3)

        uut.enter(mockk())
        val actual = actual1 && actual2 && actual3

        assertTrue(actual)
    }

    @Test
    fun whenOnExitIsCalled_ShouldStoreLambdaAsExitAction() {
        val expected = fun(_: GameContextBase) = Unit
        val uut = PhaseDefinition<DummyPhase, GameContextBase>(DummyPhase::class)
        uut.onExit(expected)

        val actual = uut.exitActions.single()

        assertEquals(expected, actual)
    }

    @Test
    fun whenExitIsCalled_ShouldCallExitAction() {
        var actual = false
        val action = fun(_: GameContextBase) {
            actual = true
        }
        val uut = PhaseDefinition<DummyPhase, GameContextBase>(DummyPhase::class)
        uut.exitActions.add(action)

        uut.exit(mockk())

        assertTrue(actual)
    }

    @Test
    fun whenExitIsCalledAndHasMultipleExitActions_AllShouldBeCalled() {
        var actual1 = false
        val action1 = fun(_: GameContextBase) {
            actual1 = true
        }
        var actual2 = false
        val action2 = fun(_: GameContextBase) {
            actual2 = true
        }
        var actual3 = false
        val action3 = fun(_: GameContextBase) {
            actual3 = true
        }
        val uut = PhaseDefinition<DummyPhase, GameContextBase>(DummyPhase::class)
        uut.exitActions.add(action1)
        uut.exitActions.add(action2)
        uut.exitActions.add(action3)

        uut.exit(mockk())
        val actual = actual1 && actual2 && actual3

        assertTrue(actual)
    }
}