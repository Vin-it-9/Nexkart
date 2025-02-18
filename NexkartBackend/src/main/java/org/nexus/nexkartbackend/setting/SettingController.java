package org.nexus.nexkartbackend.setting;

import jakarta.servlet.http.HttpServletRequest;
import org.nexus.nexkartbackend.FileUploadUtil;
import org.nexus.nexkartbackend.Repository.CurrencyRepository;
import org.nexus.nexkartbackend.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.*;


@Controller
public class SettingController {

    @Autowired
    private SettingService service;

    @Autowired
    private CurrencyRepository currencyRepo;


    @GetMapping("/settings")
    public String listAll(Model model) {

        List<Setting> listSettings = service.listAllSettings();
        List<Currency> listCurrencies = currencyRepo.findAllByOrderByNameAsc();

        model.addAttribute("listCurrencies", listCurrencies);

        for (Setting setting : listSettings) {
            model.addAttribute(setting.getKey(), setting.getValue());
        }

        return "settings/settings";
    }

    @PostMapping("/settings/save_general")
    public String saveGeneralSettings(@RequestParam("fileImage") MultipartFile multipartFile,
                                      HttpServletRequest request, RedirectAttributes ra) throws IOException {

        GeneralSettingBag settingBag = service.getGeneralSettings();
        saveSiteLogo(multipartFile, settingBag);
        saveCurrencySymbol(request, settingBag);
        updateSettingValuesFromForm(request, settingBag.list());

        ra.addFlashAttribute("message", "General settings have been saved.");

        return "redirect:/settings";
    }

    private void saveSiteLogo(MultipartFile multipartFile, GeneralSettingBag settingBag) throws IOException {

        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            String value = "/site-logo/" + fileName;
            settingBag.updateSiteLogo(value);
            String uploadDir = "./site-logo/";
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }

    }

    private void saveCurrencySymbol(HttpServletRequest request, GeneralSettingBag settingBag) {
        Integer currencyId = Integer.parseInt(request.getParameter("CURRENCY_ID"));
        Optional<Currency> findByIdResult = currencyRepo.findById(currencyId);
        if (findByIdResult.isPresent()) {
            Currency currency = findByIdResult.get();
            settingBag.updateCurrencySymbol(currency.getSymbol());
        }
    }

    private void updateSettingValuesFromForm(HttpServletRequest request, List<Setting> listSettings) {

        Map<String, SettingCategory> settingsMap = new HashMap<>();
        settingsMap.put("SITE_NAME", SettingCategory.GENERAL);
        settingsMap.put("COPYRIGHT", SettingCategory.GENERAL);
        settingsMap.put("SITE_LOGO", SettingCategory.GENERAL);
        settingsMap.put("CURRENCY_ID", SettingCategory.CURRENCY);
        settingsMap.put("CURRENCY_SYMBOL_POSITION", SettingCategory.CURRENCY);
        settingsMap.put("DECIMAL_POINT_TYPE", SettingCategory.CURRENCY);
        settingsMap.put("DECIMAL_DIGITS", SettingCategory.CURRENCY);
        settingsMap.put("THOUSANDS_POINT_TYPE", SettingCategory.CURRENCY);

      for (Map.Entry<String, SettingCategory> entry : settingsMap.entrySet()) {
            String key = entry.getKey();
            String value = request.getParameter(key);
            if (value != null) {
                Optional<Setting> existingSetting = listSettings.stream()
                        .filter(s -> s.getKey().equals(key))
                        .findFirst();
                if (existingSetting.isPresent()) {
                    existingSetting.get().setValue(value);
                } else {
                    listSettings.add(new Setting(key, value, entry.getValue()));
                }
            }
        }
        service.saveAll(listSettings);
    }

    @PostMapping("/settings/save_mail_server")
    public String saveMailServerSettings(HttpServletRequest request, RedirectAttributes ra) {

        List<Setting> mailServerSettings = service.getMailServerSettings();
        updateMailServerSettingValuesFromForm(request, mailServerSettings);

        ra.addFlashAttribute("message", "Mail server settings have been saved");
        return "redirect:/settings#mailServer";
    }

    private void updateMailServerSettingValuesFromForm(HttpServletRequest request, List<Setting> listSettings) {

        Map<String, SettingCategory> mailSettingsMap = new HashMap<>();
        mailSettingsMap.put("MAIL_HOST", SettingCategory.MAIL_SERVER);
        mailSettingsMap.put("MAIL_PORT", SettingCategory.MAIL_SERVER);
        mailSettingsMap.put("MAIL_USERNAME", SettingCategory.MAIL_SERVER);
        mailSettingsMap.put("MAIL_PASSWORD", SettingCategory.MAIL_SERVER);
        mailSettingsMap.put("SMTP_AUTH", SettingCategory.MAIL_SERVER);
        mailSettingsMap.put("SMTP_SECURED", SettingCategory.MAIL_SERVER);
        mailSettingsMap.put("MAIL_FROM", SettingCategory.MAIL_SERVER);
        mailSettingsMap.put("MAIL_SENDER_NAME", SettingCategory.MAIL_SERVER);

        for (Map.Entry<String, SettingCategory> entry : mailSettingsMap.entrySet()) {
            String key = entry.getKey();
            String value = request.getParameter(key);
            if (value != null) {
                Optional<Setting> existingSetting = listSettings.stream()
                        .filter(s -> s.getKey().equals(key))
                        .findFirst();
                if (existingSetting.isPresent()) {
                    existingSetting.get().setValue(value);
                } else {
                    listSettings.add(new Setting(key, value, entry.getValue()));
                }
            }
        }
        service.saveAll(listSettings);
    }

    @PostMapping("/settings/save_mail_templates")
    public String saveMailTemplateSettings(HttpServletRequest request, RedirectAttributes ra) {
        List<Setting> mailTemplateSettings = service.getMailTemplateSettings();
        updateMailTemplateSettingValuesFromForm(request, mailTemplateSettings);
        ra.addFlashAttribute("message", "Mail template settings have been saved");
        return "redirect:/settings#mailTemplates";
    }

    private void updateMailTemplateSettingValuesFromForm(HttpServletRequest request, List<Setting> listSettings) {
        Map<String, SettingCategory> mailTemplateMap = new HashMap<>();
       
        mailTemplateMap.put("CUSTOMER_VERIFY_SUBJECT", SettingCategory.MAIL_TEMPLATES);
        mailTemplateMap.put("CUSTOMER_VERIFY_CONTENT", SettingCategory.MAIL_TEMPLATES);

        for (Map.Entry<String, SettingCategory> entry : mailTemplateMap.entrySet()) {
            String key = entry.getKey();
            String value = request.getParameter(key);
            if (value != null) {
                Optional<Setting> existingSetting = listSettings.stream()
                        .filter(s -> s.getKey().equals(key))
                        .findFirst();
                if (existingSetting.isPresent()) {
                    existingSetting.get().setValue(value);
                } else {
                    listSettings.add(new Setting(key, value, entry.getValue()));
                }
            }
        }
        service.saveAll(listSettings);
    }


}