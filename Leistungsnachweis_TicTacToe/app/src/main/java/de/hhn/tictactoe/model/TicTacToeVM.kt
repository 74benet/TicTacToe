package de.hhn.tictactoe.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import de.hhn.tictactoe.model.Field
import de.hhn.tictactoe.model.GameModel
import de.hhn.tictactoe.model.Status

class TicTacToeVM : ViewModel() {
    var gameModel by mutableStateOf(GameModel())
    var gameField by mutableStateOf(Array(3) { rowIndex ->
        Array(3) { columnIndex -> Field(indexRow = rowIndex, indexColumn = columnIndex) }
    })

    fun resetGame() {
        gameModel = GameModel()
        gameField = Array(3) { Array(3) { Field() } }

    }

    fun selectField(rowIndex: Int, columnIndex: Int) {
        val field = gameField[rowIndex][columnIndex]
        Log.d("TicTacToeVM", "Field selected: Row ${field.indexRow}, Column ${field.indexColumn}")

        if (field.status == Status.Empty) {
            field.status = gameModel.currentPlayer
            checkEndingGame()
            if (!gameModel.isGameEnding) {
                gameModel.currentPlayer = gameModel.currentPlayer.next()
            }
        }
    }

    fun checkEndingGame() {

        for (i in 0..2) {

            if (isLineEqual(i, 0, 0, 1) || isLineEqual(0, i, 1, 0)) {
                endGame()
                return
            }
        }

        if (isLineEqual(0, 0, 1, 1) || isLineEqual(0, 2, 1, -1)) {
            endGame()
            return
        }

        if (gameField.any { row -> row.any { it.status == Status.Empty } }) {
            return
        }

        gameModel.isGameEnding = true
        gameModel.winningPlayer = Status.Empty
    }

    private fun isLineEqual(row: Int, col: Int, dRow: Int, dCol: Int): Boolean {
        val first = gameField[row][col].status
        if (first == Status.Empty) return false

        for (i in 1..2) {
            if (gameField[row + dRow * i][col + dCol * i].status != first) return false
        }
        return true
    }

    private fun endGame() {
        Log.d("TicTacToeVM", "Spiel beendet. Gewinner: ${gameModel.currentPlayer}")
        gameModel.isGameEnding = true
        gameModel.winningPlayer = gameModel.currentPlayer
        Log.d("TicTacToeVM", "isGameEnding = ${gameModel.isGameEnding}, CurrentPlayer = ${gameModel.winningPlayer}")

    }
}
