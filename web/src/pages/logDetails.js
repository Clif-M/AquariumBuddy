import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import LogClient from '../api/logClient';

const CURRENT_LOG = 'search-results';
const EMPTY_DATASTORE_STATE = {
    [CURRENT_LOG]: []
};

class LogDetails extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount', 'loadLog', 'updateLog'], this);

        // Create a enw datastore with an initial "empty" state.
        this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
        this.header = new Header(this.dataStore);
    }

    /**
     * Add the header to the page and load the MusicPlaylistClient.
     */
    mount() {
        this.header.addHeaderToPage();
        this.client = new LogClient();
        this.loadLog();
        document.getElementById('update-log-button').addEventListener('click', this.updateLog)
    }

    async loadLog() {
        const log = await this.client.getLog(new URLSearchParams(window.location.search).get('id'));
        document.getElementById('type-input').value = log.flavor;
        document.getElementById('date').value = log.logDate;
        document.getElementById('log-notes').value = log.notes;
        this.dataStore.set([CURRENT_LOG], log);
        
    }

    async updateLog() {
        
        const log = this.dataStore.get(CURRENT_LOG);
        log.flavor = document.getElementById('type-input').value;
        log.logDate = document.getElementById('date').value;
        log.notes = document.getElementById('log-notes').value;

        const results = await this.client.updateLog(log);
        this.dataStore.set([CURRENT_LOG], results);
        window.location.href="tankDetails.html?id=" + new URLSearchParams(window.location.search).get('tankId');
    }


}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const logDetails = new LogDetails();
    logDetails.mount();
};

window.addEventListener('DOMContentLoaded', main);