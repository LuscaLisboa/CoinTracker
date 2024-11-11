package com.example.cointracker.view

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.cointracker.model.detailedCrypto

@Composable
fun ResponseView(response: List<detailedCrypto?>){
    return (response.forEach{ crypto ->
        if (crypto != null) {
            Text(text = detailedCryptoText(detailedCrypto = crypto))
        }
    })
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