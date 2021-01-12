package club.cafedevelopment.reflectionsettings.container;

import club.cafedevelopment.reflectionsettings.annotation.Clamp;
import club.cafedevelopment.reflectionsettings.annotation.Setting;

import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @author Reap
 */
public final class SettingManager {
    /**
     * the static SettingManager
     */
    private static final SettingManager INSTANCE = new SettingManager();
    public static SettingManager getInstance() { return INSTANCE; }

    /**
     * the {@link SettingContainer} map.
     */
    private final Map<Object, List<SettingContainer>> containerMap = new HashMap<>();
    public Map<Object, List<SettingContainer>> getContainerMap() { return containerMap; }

    /**
     * Scans the target object, creates all the {@link SettingContainer}s by the settings, adds them to the {@link #containerMap} and returns a {@link List} of them.
     * @param target the target object being scanned.
     * @return the {@link List} of all the {@link SettingContainer}s created.
     */
    public final List<SettingContainer> acquireFrom(Object target) {
        if (containerMap.containsKey(target)) return containerMap.get(target);

        final List<SettingContainer> containers = new ArrayList<>();

        Arrays.stream(target.getClass().getDeclaredFields())
                .filter(it -> it.isAnnotationPresent(Setting.class))
                .forEach(it -> {
                    if (Modifier.isFinal(it.getModifiers())) throw new UnsupportedOperationException();

                    Setting setting = it.getDeclaredAnnotation(Setting.class);
                    Clamp clamp = setting.clamp();
                    String id = setting.id().equals("FIELD_NAME") ? it.getName() : setting.id();

                    containers.add(new SettingContainer(id, setting.description(), it, clamp.min(), clamp.max(), target));
                });

        containerMap.put(target, containers);
        return containers;
    }

    /**
     * @param target the object the {@link SettingContainer} is expected to be in.
     * @param id the {@link SettingContainer} ID being searched.
     * @param ignoreCase determines whether {@link String#equals(Object)} or {@link String#equalsIgnoreCase(String)} will be used.
     * @return A nullable {@link Optional}.
     */
    public Optional<SettingContainer> getByTargetAndId(Object target, String id, boolean ignoreCase) {
        if (!containerMap.containsKey(target)) return Optional.empty();

        for (SettingContainer container : containerMap.get(target)) {
            if (ignoreCase ? container.getId().equalsIgnoreCase(id) : container.getId().equals(id))
                return Optional.of(container);
        }

        return Optional.empty();
    }
}
