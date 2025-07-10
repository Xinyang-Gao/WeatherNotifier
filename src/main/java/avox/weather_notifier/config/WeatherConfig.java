package avox.weather_notifier.config;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class WeatherConfig {
    public static final ConfigClassHandler<WeatherConfig> CONFIG = ConfigClassHandler.createBuilder(WeatherConfig.class)
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("weather_notifier.json"))
                    .build())
            .build();

    // General
    @SerialEntry public boolean clearNotification = true;
    @SerialEntry public boolean rainNotification = true;
    @SerialEntry public boolean snowNotification = false;
    @SerialEntry public boolean thunderNotification = true;

    public static Screen configScreen(Screen parent) {
        return YetAnotherConfigLib.create(CONFIG, ((defaults, config, builder) -> builder
                .title(Text.translatable("weather_notifier.config.title"))
                .category(ConfigCategory.createBuilder()
                    .name(Text.translatable("weather_notifier.config.category.general"))

                    .option(Option.<Boolean>createBuilder()
                        .name(Text.translatable("weather_notifier.config.option.clear_notification"))
                        .description(
                            OptionDescription.createBuilder()
                                .text(Text.translatable("weather_notifier.config.option.desc.clear_notification"))
                                .image(Identifier.of("weather_notifier", "textures/gui/weather_preview/clear.png"), 320, 64)
                                .build())
                        .binding(true, () -> config.clearNotification, newVal -> config.clearNotification = newVal)
                        .controller(opt -> BooleanControllerBuilder.create(opt).coloured(true))
                        .build())
                    .option(Option.<Boolean>createBuilder()
                        .name(Text.translatable("weather_notifier.config.option.rain_notification"))
                            .description(
                                OptionDescription.createBuilder()
                                    .text(Text.translatable("weather_notifier.config.option.desc.rain_notification"))
                                    .image(Identifier.of("weather_notifier", "textures/gui/weather_preview/rain.png"), 320, 64)
                                    .build())
                        .binding(true, () -> config.rainNotification, newVal -> config.rainNotification = newVal)
                        .controller(opt -> BooleanControllerBuilder.create(opt).coloured(true))
                        .build())
                    .option(Option.<Boolean>createBuilder()
                        .name(Text.translatable("weather_notifier.config.option.snow_notification"))
                            .description(
                                OptionDescription.createBuilder()
                                    .text(Text.translatable("weather_notifier.config.option.desc.snow_notification"))
                                    .image(Identifier.of("weather_notifier", "textures/gui/weather_preview/snow.png"), 320, 64)
                                    .build())
                        .binding(false, () -> config.snowNotification, newVal -> config.snowNotification = newVal)
                        .controller(opt -> BooleanControllerBuilder.create(opt).coloured(true))
                        .build())
                    .option(Option.<Boolean>createBuilder()
                        .name(Text.translatable("weather_notifier.config.option.thunder_notification"))
                            .description(
                                OptionDescription.createBuilder()
                                    .text(Text.translatable("weather_notifier.config.option.desc.thunder_notification"))
                                    .image(Identifier.of("weather_notifier", "textures/gui/weather_preview/thunder.png"), 320, 64)
                                    .build())
                        .binding(true, () -> config.thunderNotification, newVal -> config.thunderNotification = newVal)
                        .controller(opt -> BooleanControllerBuilder.create(opt).coloured(true))
                        .build())

                    .build())

        )).generateScreen(parent);
    }
}