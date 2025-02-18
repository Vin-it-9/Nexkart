package org.nexus.nexkartbackend;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.nexus.nexkartbackend.Repository.SettingRepository;
import org.nexus.nexkartbackend.setting.Setting;
import org.nexus.nexkartbackend.setting.SettingCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class SettingRepositoryTests {

    @Autowired
    SettingRepository repo;

    @Test
    public void testCreateGeneralSettings() {

        Setting siteName = new Setting("SITE_NAME", "Shopme", SettingCategory.GENERAL);
        Setting siteLogo = new Setting("SITE_LOGO", "Shopme.png", SettingCategory.GENERAL);
        Setting copyright = new Setting("COPYRIGHT", "Copyright (C) 2021 Shopme Ltd.", SettingCategory.GENERAL);

        repo.saveAll(List.of(siteName, siteLogo, copyright));

        Iterable<Setting> iterable = repo.findAll();

        assertThat(iterable).size().isGreaterThan(0);
    }

    @Test
    public void testCreateCurrencySettings() {

        Setting currencyId = new Setting("CURRENCY_ID", "1", SettingCategory.CURRENCY);
        Setting symbol = new Setting("CURRENCY_SYMBOL", "$", SettingCategory.CURRENCY);
        Setting symbolPosition = new Setting("CURRENCY_SYMBOL_POSITION", "before", SettingCategory.CURRENCY);
        Setting decimalPointType = new Setting("DECIMAL_POINT_TYPE", "POINT", SettingCategory.CURRENCY);
        Setting decimalDigits = new Setting("DECIMAL_DIGITS", "2", SettingCategory.CURRENCY);
        Setting thousandsPointType = new Setting("THOUSANDS_POINT_TYPE", "COMMA", SettingCategory.CURRENCY);

        repo.saveAll(List.of(currencyId, symbol, symbolPosition, decimalPointType,
                decimalDigits, thousandsPointType));

    }

    @Test
    public void testCreateMailSettings() {
        // Create mail settings based on your application.properties values
        Setting mailHost = new Setting("MAIL_HOST", "smtp.gmail.com", SettingCategory.MAIL_SERVER);
        Setting mailPort = new Setting("MAIL_PORT", "587", SettingCategory.MAIL_SERVER);
        Setting mailUsername = new Setting("MAIL_USERNAME", "springboot2559@gmail.com", SettingCategory.MAIL_SERVER);
        Setting mailPassword = new Setting("MAIL_PASSWORD", "reds ccxo nfnb phgm", SettingCategory.MAIL_SERVER);
        Setting mailSmtpAuth = new Setting("MAIL_SMTP_AUTH", "true", SettingCategory.MAIL_SERVER);
        Setting mailSmtpStarttls = new Setting("MAIL_SMTP_STARTTLS_ENABLE", "true", SettingCategory.MAIL_SERVER);

        // Save all settings in one go
        repo.saveAll(List.of(mailHost, mailPort, mailUsername, mailPassword, mailSmtpAuth, mailSmtpStarttls));

    }



}