package com.example.tic_tac_toeapp

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tic_tac_toeapp.ui.theme.TictactoeAppTheme
import com.example.tic_tac_toeapp.ui.theme.buttonColor
import com.example.tic_tac_toeapp.ui.theme.deepBlue

@Composable
fun TicTacToeScreen() {
    var board by remember { mutableStateOf(List(3) { MutableList(3) { "" } }) }
    var currentPlayer by remember { mutableStateOf("X") }
    var gameOver by remember { mutableStateOf(false) }
    var winner by remember { mutableStateOf<String?>(null) }
    var draw by remember { mutableStateOf(false) }

    fun checkWinner(): String? {
        // Check rows, columns, and diagonals for a winner
        for (i in 0..2) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != "") return board[i][0]
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != "") return board[0][i]
        }
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != "") return board[0][0]
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != "") return board[0][2]
        return null
    }

    fun checkDraw(): Boolean {
        return board.all { row -> row.all { cell -> cell != "" } }
    }

    fun makeMove(row: Int, col: Int) {
        if (board[row][col] == "" && !gameOver) {
            board = board.toMutableList()
                .apply { this[row] = this[row].toMutableList().apply { this[col] = currentPlayer } }
            Log.d("TicTacToe", "Move made at ($row, $col) by $currentPlayer")
            winner = checkWinner()
            if (winner != null) {
                gameOver = true
                Log.d("TicTacToe", "Game Over! Winner: $winner")
            } else if (checkDraw()) {
                gameOver = true
                draw = true
                Log.d("TicTacToe", "Game Over! It's a draw")
            } else {
                currentPlayer = if (currentPlayer == "X") "O" else "X"
                if (currentPlayer == "O") {
                    // AI makes a random move
                    val emptyCells = board.flatMapIndexed { rowIndex, row ->
                        row.mapIndexedNotNull { colIndex, cell ->
                            if (cell == "") rowIndex to colIndex else null
                        }
                    }
                    if (emptyCells.isNotEmpty()) {
                        val (aiRow, aiCol) = emptyCells.random()
                        board = board.toMutableList().apply {
                            this[aiRow] = this[aiRow].toMutableList().apply { this[aiCol] = "O" }
                        }
                        Log.d("TicTacToe", "AI move made at ($aiRow, $aiCol)")
                        winner = checkWinner()
                        if (winner != null) {
                            gameOver = true
                            Log.d("TicTacToe", "Game Over! Winner: $winner")
                        } else if (checkDraw()) {
                            gameOver = true
                            draw = true
                            Log.d("TicTacToe", "Game Over! It's a draw")
                        } else {
                            currentPlayer = "X"
                        }
                    }
                }
            }
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize(), content = { padding ->
        Column(
            modifier = Modifier
                .background(deepBlue)
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Tic Tac Toe", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(150.dp))
            for (row in 0..2) {
                Row {
                    for (col in 0..2) {
                        Card(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .padding(5.dp),
                            colors = CardDefaults.cardColors(containerColor = buttonColor)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable {
                                        makeMove(row, col)
                                    }, contentAlignment = Alignment.Center
                            ) {
                                Text(text = board[row][col], fontSize = 35.sp)
                            }
                        }
                    }
                }
            }
            if (gameOver) {
                Text(text = if (draw) "Game Over! It's a draw" else "Game Over! Winner: $winner")
                Button(onClick = {
                    board = List(3) { MutableList(3) { "" } }
                    currentPlayer = "X"
                    gameOver = false
                    winner = null
                    draw = false
                }) {
                    Text(text = "Restart")
                }
            }
        }
    })
}

@Preview(showBackground = true)
@Composable
fun TicTacToeScreenPreview() {
    TictactoeAppTheme {
        TicTacToeScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun Butto() {


}
