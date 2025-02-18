package org.nexus.nexkartbackend.setting;

import jakarta.transaction.Transactional;
import org.nexus.nexkartbackend.Repository.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class SettingService {


    @Autowired
    private SettingRepository repo;

    public List<Setting> listAllSettings() {
        return (List<Setting>) repo.findAll();
    }

    public GeneralSettingBag getGeneralSettings() {

        List<Setting> settings = new ArrayList<>();

        List<Setting> generalSettings = repo.findByCategory(SettingCategory.GENERAL);
        List<Setting> currencySettings = repo.findByCategory(SettingCategory.CURRENCY);

        boolean siteLogoFound = generalSettings.stream()
                .anyMatch(s -> s.getKey().equals("SITE_LOGO"));
        if (!siteLogoFound) {
            Setting siteLogo = new Setting("SITE_LOGO", "Nexkart.png", SettingCategory.GENERAL);
            generalSettings.add(siteLogo);
            repo.save(siteLogo);
        }

        settings.addAll(generalSettings);
        settings.addAll(currencySettings);

        return new GeneralSettingBag(settings);
    }

    public void saveAll(Iterable<Setting> settings) {
        repo.saveAll(settings);
    }

    public List<Setting> getMailServerSettings() {
        return repo.findByCategory(SettingCategory.MAIL_SERVER);
    }

    public List<Setting> getMailTemplateSettings() {
        return repo.findByCategory(SettingCategory.MAIL_TEMPLATES);
    }


}