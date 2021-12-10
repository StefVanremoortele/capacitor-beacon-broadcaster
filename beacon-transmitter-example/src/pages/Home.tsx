import './Home.css';

import { IonContent, IonHeader, IonPage, IonTitle, IonToolbar } from '@ionic/react';

import { BeaconTransmitter } from '../plugins/BeaconTransmitter';
import ExploreContainer from '../components/ExploreContainer';

const Home: React.FC = () => {
  BeaconTransmitter.initialize({namespace: "f46e91e35cd7d2c66369", instance: "000000000001"});

  const startBroadcast = () => {
    BeaconTransmitter.startBroadcast();
  }

  const stopBroadcast = () => {
    BeaconTransmitter.stopBroadcast();
  }


  return (
    <IonPage>
      <IonHeader>
        <IonToolbar>
          <IonTitle>Blank</IonTitle>
        </IonToolbar>
      </IonHeader>
      <IonContent fullscreen>
        <IonHeader collapse="condense">
          <IonToolbar>
            <IonTitle size="large">Blank</IonTitle>
          </IonToolbar>
        </IonHeader>
        {/* <ExploreContainer /> */}
        <div>
          <span>it works!</span>
          <button onClick={startBroadcast}>Start Broadcast</button>
          <button onClick={stopBroadcast}>Stop Broadcast</button>
        </div>
      </IonContent>
    </IonPage>
  );
};

export default Home;
