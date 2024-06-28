package cn.paper_card.qq_bind;

import cn.paper_card.client.api.PaperClientApi;
import cn.paper_card.client.api.Util;
import cn.paper_card.qq_bind.api.BindInfo;
import cn.paper_card.qq_bind.api.QqBindApi;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

class QqBindApiImpl implements QqBindApi {

    private final @NotNull ThePlugin plugin;

    QqBindApiImpl(@NotNull ThePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void addBind(@NotNull UUID uuid, long qq, @NotNull String remark) {
        // TODO: 尚未支持
        throw  new UnsupportedOperationException("TODO：尚未支持");
    }

    @Override
    public boolean removeBind(@NotNull UUID uuid, long qq) {
        // TODO: 尚未支持
        throw  new UnsupportedOperationException("TODO：尚未支持");
    }

    @Override
    public @Nullable BindInfo queryByUuid(@NotNull UUID uuid) throws Exception {
        final PaperClientApi api;

        api = this.plugin.getPaperClientApi();

        final URL url;
        url = new URL(api.getApiBase() + "/qq-bind/uuid/" + uuid);

        final HttpURLConnection connection;
        connection = (HttpURLConnection) url.openConnection();

        final String data;

        data = Util.readData(connection);

        final JsonObject jsonObject = new Gson().fromJson(data, JsonObject.class);
        final String ec = jsonObject.get("ec").getAsString();
        final String em = jsonObject.get("em").getAsString();

        if (!"ok".equals(ec)) {
            throw new Exception("%s: %s".formatted(ec, em));
        }

        final JsonElement dataEle = jsonObject.get("data");

        if (dataEle == null || dataEle.isJsonNull()) {
            return null;
        }

        final JsonObject dataObj = dataEle.getAsJsonObject();

        return MyUtil.parseBindInfoJson(dataObj);
    }

    @Override
    public @Nullable BindInfo queryByQq(long qq) {
        // TODO: 尚未支持
        throw  new UnsupportedOperationException("TODO：尚未支持");
    }
}