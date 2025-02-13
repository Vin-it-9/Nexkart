package org.nexus.nexkartbackend.setting;


import org.nexus.nexkartbackend.Repository.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
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

        settings.addAll(generalSettings);
        settings.addAll(currencySettings);

        return new GeneralSettingBag(settings);
    }

    public void saveAll(Iterable<Setting> settings) {
        repo.saveAll(settings);
    }

}