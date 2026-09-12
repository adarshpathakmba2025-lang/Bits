package com.bits.app.games

import kotlin.random.Random

enum class LetterMark { CORRECT, PRESENT, ABSENT }

/** One puzzle: the answer plus the positions revealed for free at the start. */
data class WordPuzzle(val answer: String, val revealed: Set<Int>)

object Wordle {
    const val LENGTH = 5
    const val MAX_GUESSES = 6

    /** A built-in list keeps the game fully offline. */
    val answers = listOf(
        "APPLE", "BRAVE", "CRANE", "DRIFT", "EAGER", "FLAME", "GRAPE", "HOUSE",
        "IVORY", "JOLLY", "KNEEL", "LEMON", "MANGO", "NOBLE", "OCEAN", "PIANO",
        "QUIET", "RIVER", "STONE", "TIGER", "UNITY", "VIVID", "WHALE", "YACHT",
        "ZEBRA", "BLOOM", "CHARM", "DANCE", "EARTH", "FROST", "GLASS", "HONEY",
        "LIGHT", "MUSIC", "NIGHT", "PEARL", "QUILT", "ROBIN", "SUGAR", "TRUST",
        "AMBER", "BEACH", "CLOUD", "DREAM", "EMBER", "FABLE", "GHOST", "HEART",
        "INDEX", "JOKER", "KARMA", "LUNAR", "MAPLE", "NURSE", "ORBIT", "PLUMB",
        "QUEST", "RUSTY", "SHINE", "TULIP", "URBAN", "VAPOR", "WAGON", "XENON",
        "YIELD", "ZONAL", "ADOBE", "BADGE", "CABIN", "DELTA", "EQUIP", "FLINT",
        "GRIND", "HAZEL", "INLET", "JUICE", "KIOSK", "LATCH", "MERIT", "NOTCH",
        "OLIVE", "PRISM", "QUARK", "RIDGE", "SPICE", "THUMB", "ULTRA", "VOWEL",
        "WHISK", "YOUNG", "ZESTY", "ANGEL", "BRICK", "CHESS", "DOUGH", "ELBOW",
        "FUDGE", "GRASS", "HOTEL", "IMAGE", "JEWEL", "KNACK", "LODGE", "MOUNT",
        "NYLON", "ONION", "PATCH", "RAVEN", "SOLAR", "TOWEL", "VAULT", "WOVEN",
        "ALBUM", "BLAZE", "COMET", "DIZZY", "EXTRA", "FROWN", "GAUGE", "HUMID",
        "IRONY", "JUMBO", "LEAPT", "MIRTH", "NOVEL", "PLANK", "QUOTA", "ROAST",
        "SWIRL", "TREND", "USHER", "VIGOR", "WRIST", "YEAST", "ZEBUS", "ARROW",
        "BERRY", "CIDER", "DWELL", "ELECT", "FIBER", "GLOBE", "HATCH", "INPUT",
    )

    private val valid = answers.toSet()

    fun randomAnswer(random: Random = Random.Default): String = answers[random.nextInt(answers.size)]

    /**
     * Builds a puzzle with a couple of letters already shown, so a blank grid never
     * feels like a cold guess. Easier puzzles reveal more; [difficulty] counts up
     * from 0 as the player's streak grows.
     */
    fun newPuzzle(difficulty: Int = 0, random: Random = Random.Default): WordPuzzle {
        val answer = randomAnswer(random)
        val hints = when {
            difficulty <= 0 -> 2
            difficulty <= 2 -> 1
            else -> 0
        }
        val positions = (0 until LENGTH).shuffled(random).take(hints).toSet()
        return WordPuzzle(answer, positions)
    }

    fun isAcceptable(guess: String): Boolean =
        guess.length == LENGTH && guess.uppercase() in valid

    /** True when a guess respects every freely revealed letter. */
    fun matchesHints(guess: String, puzzle: WordPuzzle): Boolean =
        puzzle.revealed.all { index ->
            guess.length > index && guess[index].uppercaseChar() == puzzle.answer[index]
        }

    /**
     * Standard Wordle marking. A letter is only marked PRESENT if the answer still has
     * an unmatched copy of it, so duplicate letters behave the way players expect.
     */
    fun mark(guess: String, answer: String): List<LetterMark> {
        val g = guess.uppercase()
        val a = answer.uppercase()
        val result = MutableList(LENGTH) { LetterMark.ABSENT }
        val remaining = mutableMapOf<Char, Int>()

        for (i in 0 until LENGTH) {
            if (g[i] == a[i]) result[i] = LetterMark.CORRECT
            else remaining[a[i]] = (remaining[a[i]] ?: 0) + 1
        }
        for (i in 0 until LENGTH) {
            if (result[i] == LetterMark.CORRECT) continue
            val count = remaining[g[i]] ?: 0
            if (count > 0) {
                result[i] = LetterMark.PRESENT
                remaining[g[i]] = count - 1
            }
        }
        return result
    }
}
