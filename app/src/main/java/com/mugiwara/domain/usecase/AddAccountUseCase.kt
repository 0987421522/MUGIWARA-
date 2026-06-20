package com.mugiwara.domain.usecase

import com.mugiwara.data.mapper.AccountMapper
import com.mugiwara.data.repository.AccountRepository
import com.mugiwara.domain.model.Account
import javax.inject.Inject

class AddAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(account: Account) {
        accountRepository.addAccount(AccountMapper.toEntity(account))
    }
}
