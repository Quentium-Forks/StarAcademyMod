package abeshutt.staracademy.util;

import java.util.Optional;

public interface ProxyEntity {

    boolean isInSafariPortal();

    boolean hasSafariPortalCooldown();

    void setInSafariPortal(boolean inSafariPortal);

    void setSafariPortalCooldown(boolean safariPortalCooldown);

    static Optional<ProxyEntity> of(Object object) {
        if(object instanceof ProxyEntity proxy) {
            return Optional.of(proxy);
        }

        return Optional.empty();
    }

}
