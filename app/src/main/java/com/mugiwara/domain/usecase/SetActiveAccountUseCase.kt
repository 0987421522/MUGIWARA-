package com.mugiwara.domain.usecase

import com.mugiwara.data.repository.AccountRepository
import javax.inject.Inject

class SetActiveAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(id: String) {
        accountRepository.setActiveAccount(id)
    }
}
