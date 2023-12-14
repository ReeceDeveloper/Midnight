/*
 * The MIT License (MIT)
 *
 * Copyright © 2023 - ReeceDeveloper
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
 * to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 * the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
 * THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package reecedeveloper.com.github.embeds;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class Embeds {
    public static final Color INFORMATION_COLOR = Color.decode("#004C4C");
    public static final Color WARNING_COLOR = Color.decode("#999900");
    public static final Color ERROR_COLOR = Color.decode("#990000");

    private static MessageEmbed createEmbed(String message, Color color) {
        return new EmbedBuilder()
                .setDescription(message)
                .setColor(color)
                .build();
    }

    public static MessageEmbed informationEmbed(String informationMessage) {
        return createEmbed(informationMessage, INFORMATION_COLOR);
    }

    public static MessageEmbed warningEmbed(String warningMessage) {
        return createEmbed(warningMessage, WARNING_COLOR);
    }

    public static MessageEmbed errorEmbed(String errorMessage) {
        return createEmbed(errorMessage, ERROR_COLOR);
    }
}
