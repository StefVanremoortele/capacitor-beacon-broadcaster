export interface BeaconTransmitterPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
