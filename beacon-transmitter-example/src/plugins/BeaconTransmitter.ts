import { Plugin, registerPlugin } from "@capacitor/core";

interface BeaconTransmitterPluginInterface extends Plugin {
  initialize: ({namespace, instance}: any) => Promise<any>;
  setNamespace: (namespace: string) => Promise<any>;
  setInstance: (instance: string) => Promise<any>;
  startBroadcast: () => Promise<any>;
  stopBroadcast: () => Promise<any>;
}

export const BeaconTransmitter = registerPlugin<BeaconTransmitterPluginInterface>(
  "BeaconTransmitter"
);