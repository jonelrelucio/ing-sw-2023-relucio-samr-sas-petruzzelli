package it.polimi.ingsw.model.commonGoalCard;

import com.google.gson.*;

import java.lang.reflect.Type;

public class CommonGoalCardDeserializer implements JsonDeserializer<CommonGoalCard> {
    @Override
    public CommonGoalCard deserialize(JsonElement json, Type typeOf, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = (JsonObject) json;
        JsonElement typeObj = jsonObject.get("cardType");

        if (typeObj != null) {
            String cardType = typeObj.getAsString();

            switch (cardType) {
                case "CommonGoalExactShape" -> {
                    return context.deserialize(json, CommonGoalExactShape.class);
                }
                case "CommonGoalShape" -> {
                    return context.deserialize(json, CommonGoalShape.class);
                }
                case "CommonGoalSameTypeGroup" -> {
                    return context.deserialize(json, CommonGoalSameTypeGroup.class);
                }
                case "CommonGoalDifferentType" -> {
                    return context.deserialize(json, CommonGoalDifferentType.class);
                }
            }
        }

        return null;
    }
}
