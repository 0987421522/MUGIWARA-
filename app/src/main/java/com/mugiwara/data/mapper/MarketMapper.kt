package com.mugiwara.data.mapper

import com.mugiwara.data.model.MarketEntity
import com.mugiwara.data.remote.dto.MarketPriceResponse
import com.mugiwara.domain.model.Market
import com.mugiwara.domain.model.MarketCategory

object MarketMapper {
    fun toDomain(entity: MarketEntity): Market {
        return Market(
            id = entity.id,
            name = entity.name,
            symbol = entity.symbol,
            category = MarketCategory.valueOf(entity.category.uppercase()),
            price = entity.price,
            bid = entity.bid,
            ask = entity.ask,
            spread = entity.spread,
            change = entity.change,
            changePercent = entity.changePercent,
            high24h = entity.high24h,
            low24h = entity.low24h,
            volume24h = entity.volume24h,
            isOpen = entity.isOpen
        )
    }
    
    fun toEntity(domain: Market): MarketEntity {
        return MarketEntity(
            id = domain.id,
            name = domain.name,
            symbol = domain.symbol,
            category = domain.category.name,
            price = domain.price,
            bid = domain.bid,
            ask = domain.ask,
            spread = domain.spread,
            change = domain.change,
            changePercent = domain.changePercent,
            high24h = domain.high24h,
            low24h = domain.low24h,
            volume24h = domain.volume24h,
            isOpen = domain.isOpen,
            openTime = 0,
            closeTime = 0,
            timezone = "UTC",
            updatedAt = System.currentTimeMillis()
        )
    }
    
    fun fromDto(dto: MarketPriceResponse): MarketEntity {
        return MarketEntity(
            id = dto.symbol,
            name = dto.symbol,
            symbol = dto.symbol,
            category = "FOREX",
            price = (dto.bid + dto.ask) / 2,
            bid = dto.bid,
            ask = dto.ask,
            spread = dto.spread,
            change = dto.change,
            changePercent = dto.changePercent,
            high24h = dto.high,
            low24h = dto.low,
            volume24h = dto.volume,
            isOpen = true,
            openTime = 0,
            closeTime = 0,
            timezone = "UTC",
            updatedAt = System.currentTimeMillis()
        )
    }
}
