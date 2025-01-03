package abeshutt.staracademy.util;

import net.minecraft.text.Text;

import java.util.Optional;

public class TextUtils {

    public static Text replace(Text text, String target, Text replacement) {
        text.getContent().visit((string) -> {
            return Optional.empty();
        });

        return text;
    }

}
