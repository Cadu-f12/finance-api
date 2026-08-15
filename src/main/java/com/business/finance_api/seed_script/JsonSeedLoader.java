package com.business.finance_api.seed_script;

import com.business.finance_api.entities.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class JsonSeedLoader {

    private final ObjectMapper objectMapper;

    public JsonSeedLoader(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<MonthlyClosingEntity> getMonthlyClosingFromJSON(String path) {
        return readListResource(path, new TypeReference<List<MonthlyClosingEntity>>() {});
    }

    public List<ModalitiesEntity> getModalitiesFromJSON(String path) {
        return readListResource(path, new TypeReference<List<ModalitiesEntity>>() {});
    }

    public List<ExpenseCategoriesEntity> getExpensesFromJSON(String path) {
        return readListResource(path, new TypeReference<List<ExpenseCategoriesEntity>>() {});
    }

    public List<MonthlyExpenseSeedDTO> getMonthExpenseFromJSON(String path) {
        return readListResource(path, new TypeReference<List<MonthlyExpenseSeedDTO>>() {});
    }

    public List<InvestmentAllocationSeedDTO> getInvestmentFromJSON(String path) {
        return readListResource(path, new TypeReference<List<InvestmentAllocationSeedDTO>>() {});
    }

    private <T> List<T> readListResource(String path, TypeReference<List<T>> typeReference) {
        try (InputStream inputStream = new ClassPathResource(path).getInputStream()) {
            return objectMapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar o arquivo JSON [" + path + "]: " + e.getMessage(), e);
        }
    }
}