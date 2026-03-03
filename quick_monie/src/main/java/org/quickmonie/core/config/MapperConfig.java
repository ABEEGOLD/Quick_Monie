package org.quickmonie.core.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.quickmonie.core.dto.response.AccountResponse;
import org.quickmonie.core.dto.response.TransactionResponse;
import org.quickmonie.core.dto.response.UpdateAccountResponse;
import org.quickmonie.core.model.Account;
import org.quickmonie.core.model.Transaction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(Transaction.class, TransactionResponse.class)
                   .addMapping(Transaction::getId,
                           TransactionResponse::setTransactionId);
        configureMatchingStrategy(modelMapper);
        modelMapper.typeMap(Account.class, UpdateAccountResponse.class)
                .addMapping(Account::getId,
                        UpdateAccountResponse::setAccountId);
        configureForAccountResponse(modelMapper);
        return modelMapper;
    }

    private static void configureForAccountResponse(ModelMapper modelMapper) {
        modelMapper.typeMap(Account.class, AccountResponse.class)
                .addMapping(Account::getVerificationDocument,
                        AccountResponse::setKycDocument);
    }

    private void configureMatchingStrategy(ModelMapper modelMapper) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

    }

}
