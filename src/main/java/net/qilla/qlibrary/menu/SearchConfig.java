package net.qilla.qlibrary.menu;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public final class SearchConfig {

    private final Builder builder;
    private final int searchIndex;
    private final int resetSearchIndex;

    private SearchConfig(@NotNull Builder builder) {
        Preconditions.checkNotNull(builder, "Builder cannot be null");

        this.builder = builder;
        this.searchIndex = builder.searchIndex;
        this.resetSearchIndex = builder.resetSearchIndex;
    }

    public static SearchConfig of(Consumer<Builder> builder) {
        Builder newBuilder = new Builder();
        builder.accept(newBuilder);
        return new SearchConfig(newBuilder);
    }

    public Builder searchBuilder() {
        return this.builder;
    }

    public int searchIndex() {
        return this.searchIndex;
    }

    public int resetSearchIndex() {
        return this.resetSearchIndex;
    }

    public static class Builder {

        protected int searchIndex;
        protected int resetSearchIndex;

        protected Builder() {
            this.searchIndex = 0;
            this.resetSearchIndex = 0;
        }

        public Builder searchIndex(int index) {
            this.searchIndex = index;
            return this;
        }

        public Builder resetSearchIndex(int index) {
            this.resetSearchIndex = index;
            return this;
        }
    }
}
