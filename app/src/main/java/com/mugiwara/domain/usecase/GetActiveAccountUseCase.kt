package com.mugiwara.domain.usecase

import com.mugiwara.data.mapper.AccountMapper
import com.mugiwara.data.repository.AccountRepository
import com.mugiwara.domain.model.Account
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetActiveAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    operator fun invoke(): Flow<Account?> {
        return accountRepository.getActiveAccount().map { entity ->
            entity?.let { AccountMapper.toDomain(it) }
        }
    }
}
