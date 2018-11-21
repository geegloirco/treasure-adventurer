package ir.geeglo.dev.treasure.rest;

import ir.piana.dev.core.annotation.AssetHandler;
import ir.piana.dev.core.annotation.Handler;
import ir.piana.dev.core.annotation.HandlerType;

/**
 * @author Mohammad Rahmati, 10/13/2018
 */
@Handler(baseUrl = "/", handlerType = HandlerType.ASSET_HANDLER)
@AssetHandler(assetPath = "adventurer-client/src")
//@AssetHandler(assetPath = "adventurer-client/dist/treasure-client")
public class RootAssetHandler {
}
