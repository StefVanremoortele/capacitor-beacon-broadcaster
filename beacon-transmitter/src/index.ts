import { registerPlugin } from '@capacitor/core';

import type { BeaconTransmitterPlugin } from './definitions';

const BeaconTransmitter = registerPlugin<BeaconTransmitterPlugin>('BeaconTransmitter', {
  web: () => import('./web').then(m => new m.BeaconTransmitterWeb()),
});

export * from './definitions';
export { BeaconTransmitter };
