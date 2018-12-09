package com.avalonomnimedia.playingcardsengine

import io.mockk.mockk
import org.junit.Assert
import org.junit.Test

class TestPhaseDefinition {
    class DummyPhase : IGamePhase
    class DummyAction : IGameAction

    @Test
    fun `when 'on' is called, should store transition with action as key`() {
        val uut = PhaseDefinition<DummyPhase, GameContextBase>(DummyPhase::class)
        uut.on(DummyAction::class) {
            DummyPhase::class
        }

        val actual = uut.transitions[DummyAction::class]

        Assert.assertNotNull(actual)
    }

    @Test
    fun `when inline 'on' is called, should store transition with action as key`() {
        val uut = PhaseDefinition<DummyPhase, GameContextBase>(DummyPhase::class)
        uut.on<DummyAction> {
            DummyPhase::class
        }

        val actual = uut.transitions[DummyAction::class]

        Assert.assertNotNull(actual)
    }

    @Test
    fun `when transitionTo is called, should return generic as KClass`() {
        val expected = DummyPhase::class
        val uut = PhaseDefinition<DummyPhase, GameContextBase>(DummyPhase::class)

        val actual = uut.transitionTo<DummyPhase>()

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `when onEntry is called, should store lambda as entryAction `() {
        val expected = fun(_: GameContextBase) = Unit
        val uut = PhaseDefinition<DummyPhase, GameContextBase>(DummyPhase::class)
        uut.onEntry(expected)

        val actual = uut.entryActions.single()

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `when enter is called, should call entryAction`() {
        var actual = false
        val action = fun(_: GameContextBase) {
            actual = true
        }
        val uut = PhaseDefinition<DummyPhase, GameContextBase>(DummyPhase::class)
        uut.entryActions.add(action)

        uut.enter(mockk())

        Assert.assertTrue(actual)
    }

    @Test
    fun `when enter is called and has multiple entryActions, all should be called`() {
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

        Assert.assertTrue(actual)
    }

    @Test
    fun `when onExit is called, should store lambda as exitAction `() {
        val expected = fun(_: GameContextBase) = Unit
        val uut = PhaseDefinition<DummyPhase, GameContextBase>(DummyPhase::class)
        uut.onExit(expected)

        val actual = uut.exitActions.single()

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `when exit is called, should call exitAction`() {
        var actual = false
        val action = fun(_: GameContextBase) {
            actual = true
        }
        val uut = PhaseDefinition<DummyPhase, GameContextBase>(DummyPhase::class)
        uut.exitActions.add(action)

        uut.exit(mockk())

        Assert.assertTrue(actual)
    }

    @Test
    fun `when exit is called and has multiple exitActions, all should be called`() {
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

        Assert.assertTrue(actual)
    }
}