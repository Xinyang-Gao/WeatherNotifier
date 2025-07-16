package avox.weather_notifier;

import avox.weather_notifier.config.WeatherConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class WeatherToast implements Toast {
    private static final Identifier TEXTURE = Identifier.ofVanilla("toast/advancement");
    private Toast.Visibility visibility = Visibility.SHOW;

    private final String title;
    private final String message;
    private final Identifier icon;

    public static final Identifier NOTIFICATION_SOUND_ID = Identifier.of("weather_notifier", "notification_sound");
    public static SoundEvent NOTIFICATION_SOUND_EVENT = SoundEvent.of(NOTIFICATION_SOUND_ID);

    public WeatherToast(MinecraftClient client, WeatherTypes weather) {
        this.icon = Identifier.of("weather_notifier", "textures/gui/weather_icons/" + weather.name().toLowerCase() + ".png");
        this.title = (weather.equals(WeatherTypes.CLEAR) ? "§e" : "§b") + Text.translatable("weather_notifier.toast.title").getString();
        this.message = String.format(Text.translatable("weather_notifier.toast.message." + weather.name().toLowerCase()).getString());
        if (client.player != null && WeatherConfig.CONFIG.instance().useNotificationSound) {
            client.player.playSound(NOTIFICATION_SOUND_EVENT);
        }
    }

    @Override
    public Visibility getVisibility() {
        return visibility;
    }

    @Override
    public void update(ToastManager manager, long time) {
        visibility = time >= 5000 * manager.getNotificationDisplayTimeMultiplier()
            ? Visibility.HIDE
            : Visibility.SHOW;
    }

    @Override
    public void draw(DrawContext context, TextRenderer textRenderer, long startTime) {
        context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, 0, 0, getWidth(), getHeight());
        context.drawText(textRenderer, title, 30, 7, -256, false);
        context.drawText(textRenderer, message, 30, 18, -1, false);

        context.drawTexture(RenderPipelines.GUI_TEXTURED, icon, 8, 8, 0, 0, 16, 16, 16, 16);
    }

    @Override
    public int getWidth() {
        return 160;
    }

    @Override
    public int getHeight() {
        return 32;
    }
}
