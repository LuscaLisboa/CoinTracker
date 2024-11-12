package com.example.cointracker.view

import android.text.Layout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat.Style
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.cointracker.model.detailedCrypto

@Composable
fun ResponseView(response: List<detailedCrypto?>){

    Column(modifier = Modifier
        .fillMaxSize()
    ){
        response.forEach{ crypto ->
            if (crypto != null) {
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                ){
                    Column (modifier = Modifier
                        ){
                        Row {
                            Text(text = crypto.name,
                                modifier = Modifier,
                                style = TextStyle(
                                    fontSize = 25.sp,
                                    color = Color.White
                                )
                            )
                        }
                        Row (modifier = Modifier
                            .height(75.dp)
                        ){
                            Image(
                                painter = rememberAsyncImagePainter(model = crypto.image),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxHeight()
                            )
                        }
                    }
                    Column (modifier = Modifier){
                        Text(text = crypto.symbol,
                            style = TextStyle(
                                fontSize = 20.sp,
                                color = Color.White
                            ))
                    }
                }
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Red)
                    .fillMaxHeight()
                ){

                }

                Text(text = detailedCryptoText(detailedCrypto = crypto))
            }
        }
    }
}

fun detailedCryptoText(detailedCrypto: detailedCrypto): String {
    try {
        return """
            id: ${detailedCrypto.id}
            symbol: ${detailedCrypto.symbol}
            name: ${detailedCrypto.name}
            image: ${detailedCrypto.image}
            current_price: ${detailedCrypto.current_price}
            market_cap: ${detailedCrypto.market_cap}
            market_cap_rank: ${detailedCrypto.market_cap_rank}
            fully_diluted_valuation: ${detailedCrypto.fully_diluted_valuation}
            total_volume: ${detailedCrypto.total_volume}
            high_24h: ${detailedCrypto.high_24h}
            low_24h: ${detailedCrypto.low_24h}
            price_change_24h: ${detailedCrypto.price_change_24h}
            price_change_percentage_24h: ${detailedCrypto.price_change_percentage_24h}
            market_cap_change_24h: ${detailedCrypto.market_cap_change_24h}
            market_cap_change_percentage_24h: ${detailedCrypto.market_cap_change_percentage_24h}
            circulating_supply: ${detailedCrypto.circulating_supply}
            total_supply: ${detailedCrypto.total_supply}
            max_supply: ${detailedCrypto.max_supply}
            ath: ${detailedCrypto.ath}
            ath_change_percentage: ${detailedCrypto.ath_change_percentage}
            ath_date: ${detailedCrypto.ath_date}
            atl: ${detailedCrypto.atl}
            atl_change_percentage: ${detailedCrypto.atl_change_percentage}
            atl_date: ${detailedCrypto.atl_date}
            roi_times: ${detailedCrypto.roi?.times ?: "N/A"}
            roi_currency: ${detailedCrypto.roi?.currency ?: "N/A"}
            roi_percentage: ${detailedCrypto.roi?.percentage ?: "N/A"}
            last_updated: ${detailedCrypto.last_updated}
        """
    } catch (e: Exception) {
        return "Erro ao carregar dados."
    }
}