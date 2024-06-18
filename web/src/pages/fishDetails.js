import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import FishClient from '../api/fishClient';

const CURRENT_FISH = 'search-results';
const EMPTY_DATASTORE_STATE = {
    [CURRENT_FISH]: []
};

class FishDetails extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount', 'loadFish', 'updateFish'], this);

        // Create a enw datastore with an initial "empty" state.
        this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
        this.header = new Header(this.dataStore);
    }

    /**
     * Add the header to the page and load the MusicPlaylistClient.
     */
    mount() {
        this.header.addHeaderToPage();
        this.client = new FishClient();
        this.loadFish();
        document.getElementById('update-fish-button').addEventListener('click', this.updateFish)
    }

    async loadFish() {
        const fish = await this.client.getSingleFish(new URLSearchParams(window.location.search).get('id'));
        document.getElementById('fish-name').value = fish.name;
        document.getElementById('fish-url').value = fish.imageUrl;
        document.getElementById('fish-species').value = fish.species;
        this.dataStore.set([CURRENT_FISH], fish);

    }

    async updateFish() {

        const fish = this.dataStore.get(CURRENT_FISH);
        fish.name = document.getElementById('fish-name').value;
        fish.imageUrl = document.getElementById('fish-url').value;
        fish.species = document.getElementById('fish-species').value;

        const results = await this.client.updateFish(fish);
        this.dataStore.set([CURRENT_FISH], results);
        window.location.href = "fishList.html?id=" + new URLSearchParams(window.location.search).get('tankId');
    }


}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const fishDetails = new FishDetails();
    fishDetails.mount();
};

window.addEventListener('DOMContentLoaded', main);