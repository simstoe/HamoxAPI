package net.haxzee.hamoxapi.databaseutils;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.sql.SQLException;

@FunctionalInterface
public interface SQLFunction<I, O> {
    @Nullable
    O apply(@NotNull I paramI) throws SQLException;
}

