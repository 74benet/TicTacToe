package de.hhn.tictactoe.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.hhn.tictactoe.model.Status
import de.hhn.tictactoe.R
import de.hhn.tictactoe.model.Field
import de.hhn.tictactoe.model.GameModel
import de.hhn.tictactoe.viewmodel.TicTacToeVM
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.math.log

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(viewModel: TicTacToeVM) {


    val currentPlayer = viewModel.gameModel.currentPlayer

    var isGameEnding = viewModel.gameModel.isGameEnding
    val winningPlayer = viewModel.gameModel.winningPlayer
    val winningPlayerColor = Field(winningPlayer).showColor()
    val gameField = viewModel.gameField
    var currentPlayerMSO = remember { mutableStateOf(Status.PlayerO) }
    var gameEnded = remember{mutableStateOf(false)}



    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 40.dp),
            color = Color.Black
        )
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                if (currentPlayerMSO.value == Status.PlayerO) {
                    Text(
                        text = Status.PlayerX.toString(),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 50.dp),
                        color = Color.Blue
                    )
                    Text(
                        text = Status.PlayerO.toString(),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 50.dp),
                        color = Color.Red
                    )
                }
                if (currentPlayerMSO.value == Status.PlayerX) {
                    Text(
                        text = Status.PlayerX.toString(),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 50.dp),
                        color = Color.Blue
                    )
                    Text(
                        text = Status.PlayerO.toString(),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 50.dp),
                        color = Color.Red
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                gameField.forEachIndexed { rowIndex, rows ->

                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                    ) {
                        rows.forEachIndexed { columnIndex,field ->
                            var text = mutableStateOf(field.showText())
                            var color = mutableStateOf(field.showColor())
                            Card(
                                modifier = Modifier
                                    .padding(all = 2.dp)
                                    .border(
                                        width = 2.dp,
                                        color = Color.Gray,
                                        shape = RoundedCornerShape(5.dp),
                                    )
                                    .height(111.dp)
                                    .width(111.dp),
                                onClick = {

                                    currentPlayerMSO.value = currentPlayer
                                    if (!isGameEnding) {
                                        viewModel.selectField(rowIndex, columnIndex)
                                        text.value = field.showText()
                                        color.value = field.showColor()
                                        gameEnded.value = viewModel.gameModel.isGameEnding
                                    }
                                }
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Text(
                                        text = text.value,
                                        fontSize = 60.sp,
                                        color = color.value,
                                    )
                                }
                            }
                        }
                    }
                }
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    if (gameEnded.value) {
                        Text(
                            text = "Winning: $winningPlayer",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 25.dp),
                            color = winningPlayerColor
                        )
                    }
                }
            }
        }
    }
}
