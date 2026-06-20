package com.mugiwara.domain.usecase

import com.mugiwara.data.mapper.AccountMapper
import com.mugiwara.data.repository.AccountRepository
import com.mugiwara.domain.model.Account
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAccountsUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    operator fun invoke(): Flow<List<Account>> {
        return accountRepository.getAllAccounts().map { entities ->
            entities.map { AccountMapper.toDomain(it) }
        }
    }
}
