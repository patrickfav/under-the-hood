package at.favre.lib.hood;

import at.favre.lib.hood.interfaces.HoodAPI;
import at.favre.lib.hood.noop.HoodNoop;

final class HoodFactory implements HoodAPI.Factory {
    @Override
    public HoodAPI createHoodApi() {
        return new HoodNoop();
    }

    @Override
    public HoodAPI.Extension createHoodApiExtension() {
        return new HoodNoop.HoodExtensionNoop();
    }
}
