import { WebPlugin } from '@capacitor/core';

import type { BeaconTransmitterPlugin } from './definitions';

export class BeaconTransmitterWeb extends WebPlugin implements BeaconTransmitterPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
