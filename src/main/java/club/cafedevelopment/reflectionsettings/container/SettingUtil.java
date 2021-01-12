package club.cafedevelopment.reflectionsettings.container;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Reap
 */
public final class SettingUtil {
    private static final SettingManager manager = SettingManager.getInstance();

    /**
     * @param target     the target object
     * @param id         the setting ID
     * @param ignoreCase should we use {@link String#equals(Object)} or {@link String#equalsIgnoreCase(String)}
     * @return the wanted setting container if it exists
     * @throws NoSuchElementException if no container has been found
     */
    public static SettingContainer retrieve(Object target, String id, boolean ignoreCase) throws NoSuchElementException {
        if (!manager.getContainerMap().containsKey(target)) manager.acquireFrom(target);

        return manager.getByTargetAndId(target, id, ignoreCase).orElseThrow(NoSuchElementException::new);
    }

    /**
     * @param clazz class type
     * @param <T> type
     * @return all the containers with the value type {@link T}
     */
    public static <T> List<SettingContainer> getContainersOfType(Class<T> clazz) {
        List<SettingContainer> settings = new ArrayList<>();

        for (List<SettingContainer> containerList : manager.getContainerMap().values()) {
            for (SettingContainer container : containerList) {
                if (container.getValue().getClass().isAssignableFrom(clazz)) settings.add(container);
            }
        }

        return settings;
    }
}
