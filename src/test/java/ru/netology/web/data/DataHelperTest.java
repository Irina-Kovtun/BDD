package ru.netology.web.data;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataHelperTest {

    public static int generateIndexForTransferFromCard(int limit, int exclusion) {
        Faker faker = new Faker();
        int newIndex = faker.random().nextInt(0, limit);
        while (newIndex == exclusion) {
            newIndex = faker.random().nextInt(0, limit);
        }
        return newIndex;
    }

    @Test
    public void shouldcalc() {
        int result = generateIndexForTransferFromCard(1, 0);
        System.out.println(result);
    }
}