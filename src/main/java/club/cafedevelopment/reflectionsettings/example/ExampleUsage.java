package club.cafedevelopment.reflectionsettings.example;

import club.cafedevelopment.reflectionsettings.annotation.Clamp;
import club.cafedevelopment.reflectionsettings.annotation.Setting;
import club.cafedevelopment.reflectionsettings.container.SettingUtil;
import club.cafedevelopment.reflectionsettings.container.SettingContainer;

/**
 * @author Reap
 */
public final class ExampleUsage {
    @Setting(id = "BooleanSetting")
    public boolean aBoolean = true;

    @Setting(clamp = @Clamp(min = 100.0, max = 1000.0))
    public int anInt = 450;

    @Setting(description = "Description provided!")
    public String descriptive = "This is pretty descriptive.";

    public static void main(String... args) {
        ExampleUsage instance = new ExampleUsage();

        SettingContainer aBooleanContainer = SettingUtil.retrieve(instance, "BooleanSetting", true);
        SettingContainer anIntContainer = SettingUtil.retrieve(instance, "anInt", true);
        SettingContainer aDescriptiveContainer = SettingUtil.retrieve(instance, "descriptive", true);

        System.out.println(instance.aBoolean + " " + instance.anInt + " " + instance.descriptive);

        System.out.println(aBooleanContainer.getDebugInfo());
        System.out.println(anIntContainer.getDebugInfo());
        System.out.println(aDescriptiveContainer.getDebugInfo());

        instance.aBoolean = false;
        System.out.println(aBooleanContainer.getDebugInfo());

        anIntContainer.setValue(0);
        System.out.println(anIntContainer.getDebugInfo());

        aDescriptiveContainer.setValue("Follow CafeDevelopment and yagel15637 on GitHub!");
        System.out.println(aDescriptiveContainer.getDebugInfo());
    }
}
