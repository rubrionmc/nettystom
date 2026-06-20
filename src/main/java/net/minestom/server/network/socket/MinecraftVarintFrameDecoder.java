// Déclaration du paquet de ce fichier
package net.minestom.server.network.socket;

// Import d'une classe nécessaire
import io.netty.buffer.ByteBuf;
// Import d'une classe nécessaire
import io.netty.channel.ChannelHandlerContext;
// Import d'une classe nécessaire
import io.netty.handler.codec.ByteToMessageDecoder;

// Import d'une classe nécessaire
import java.util.List;

/**
 * Netty {@link ByteToMessageDecoder} that frames Minecraft's varint-length-prefixed
 * packets.
 *
 * <p>Minecraft sends packets as:
 * <pre>
 *   [VarInt: packet length][packet bytes …]
 * </pre>
 *
 * This decoder accumulates bytes until a full frame is available, then passes
 * the <em>payload</em> (without the length prefix) downstream.
 *
 * <p>No {@code java.nio.*} imports are used.
 */
// Déclaration de type (classe/interface/enum/record)
public final class MinecraftVarintFrameDecoder extends ByteToMessageDecoder {

    /** Hard limit to guard against memory-exhaustion attacks (2 MiB). */
    // Affecte une valeur
    private static final int MAX_PACKET_LENGTH = 1 << 21; // 2 097 152

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        // Embranchement : vérifie une condition
        if (!ctx.channel().isActive()) return;

        // Appelle une méthode
        in.markReaderIndex();
        // Affecte une valeur
        int length = 0;
        // Affecte une valeur
        int shift = 0;

        // Boucle : répète un bloc
        for (int i = 0; i < 5; i++) {
            // Embranchement : vérifie une condition
            if (!in.isReadable()) {
                // Appelle une méthode
                in.resetReaderIndex();
                // Renvoie une valeur à l'appelant
                return;
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            byte b = in.readByte();
            // Appelle une méthode
            length |= (b & 0x7F) << shift;
            // Embranchement : vérifie une condition
            if ((b & 0x80) == 0) {
                // Embranchement : vérifie une condition
                if (length < 0) throw new RuntimeException("Negative Paketlänge");

                // Embranchement : vérifie une condition
                if (in.readableBytes() < length) {
                    // Appelle une méthode
                    in.resetReaderIndex();
                    // Renvoie une valeur à l'appelant
                    return;
                // Fin d'un bloc/d'une expression
                }

                // Appelle une méthode
                int endIndex = in.readerIndex() + length;
                // Appelle une méthode
                in.resetReaderIndex();
                // Appelle une méthode
                out.add(in.readRetainedSlice(endIndex - in.readerIndex()));
                // Renvoie une valeur à l'appelant
                return;
            // Fin d'un bloc/d'une expression
            }
            // Instruction de code
            shift += 7;
        // Fin d'un bloc/d'une expression
        }
        // Lève une exception
        throw new RuntimeException("VarInt zu lang (Corrupted Stream)");
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}