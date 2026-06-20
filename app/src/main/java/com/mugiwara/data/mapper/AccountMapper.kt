package com.mugiwara.data.mapper

import com.mugiwara.data.model.AccountEntity
import com.mugiwara.domain.model.Account

object AccountMapper {
    fun toDomain(entity: AccountEntity): Account {
        return Account(
            id = entity.id,
            name = entity.name,
            server = entity.server,
            login = entity.login,
            balance = entity.balance,
            equity = entity.equity,
            margin = entity.margin,
            freeMargin = entity.freeMargin,
            isConnected = entity.isConnected,
            isActive = entity.isActive
        )
    }
    
    fun toEntity(domain: Account): AccountEntity {
        return AccountEntity(
            id = domain.id,
            name = domain.name,
            server = domain.server,
            login = domain.login,
            password = "",
            balance = domain.balance,
            equity = domain.equity,
            margin = domain.margin,
            freeMargin = domain.freeMargin,
            isConnected = domain.isConnected,
            isActive = domain.isActive,
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )
    }
}
