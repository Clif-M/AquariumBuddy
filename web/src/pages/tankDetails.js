import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import TankClient from '../api/tankClient';
import LogClient from '../api/logClient';
/*
The code below this comment is equivalent to...
const EMPTY_DATASTORE_STATE = {
    'search-criteria': '',
    'search-results': [],
};

...but uses the "KEY" constants instead of "magic strings".
The "KEY" constants will be reused a few times below.
*/

const TANK_KEY = 'tank-key';
const LOGS_LOADED = 'logs-key';

const SEARCH_CRITERIA_KEY = 'search-criteria';
const SEARCH_RESULTS_KEY = 'search-results';
const EMPTY_DATASTORE_STATE = {
    [SEARCH_CRITERIA_KEY]: '',
    [SEARCH_RESULTS_KEY]: [],
};


/**
 * Logic needed for the landing page of the website.
 */
class TankDetails extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount', 'search', 'displaySearchResults', 'getHTMLForSearchResults', 'updateTank', 'createLog', 'deleteLog', 'getLogsByType', 'loadTank'], this);

        // Create a enw datastore with an initial "empty" state.
        this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
        this.header = new Header(this.dataStore);
        this.dataStore.addChangeListener(this.displaySearchResults);
    }

    /**
     * Add the header to the page and load the MusicPlaylistClient.
     */
    mount() {
        this.header.addHeaderToPage();
        this.client = new TankClient();
        this.logClient = new LogClient();
        this.search();
        this.loadTank();
        document.getElementById('update-name').addEventListener('click', this.updateTank);
        document.getElementById('create-log-button').addEventListener('click', this.createLog);
        document.getElementById('type-filter').addEventListener('change', this.getLogsByType);


    }

    async createLog() {
        const tankId = this.dataStore.get(TANK_KEY).tankId;
        const logType = document.getElementById('type-input').value;
        const date = document.getElementById('date').value;
        if(!date) {return alert("Date field required.")}
        const notes = document.getElementById('log-notes').value;
        var log = await this.logClient.createLog(logType, tankId, notes, date);

        var logList = this.dataStore.get(SEARCH_RESULTS_KEY);
        logList.unshift(log);
        this.dataStore.set([SEARCH_RESULTS_KEY], logList);

    }

    async getLogsByType() {

        const tankId = this.dataStore.get(TANK_KEY).tankId;
        const logType = document.getElementById('type-filter').value;
        this.dataStore.set([SEARCH_RESULTS_KEY], null);
        if (logType === "All") {
            return this.search();
        } else {
            const results = await this.logClient.getLogsByType(tankId, logType);
            this.dataStore.set([SEARCH_RESULTS_KEY], results);

        }

    }

    async deleteLog(logId) {
        var logList = this.dataStore.get(SEARCH_RESULTS_KEY);
        logList = logList.filter(log => log.logId !== logId);
        this.dataStore.set([SEARCH_RESULTS_KEY], logList);
        await this.logClient.deleteLog(logId);
    }

    async updateTank() {
        const name = document.getElementById('tank-name').value;
        const tank = this.dataStore.get(TANK_KEY);
        tank.name = name;

        const results = await this.client.updateTank(tank);
        this.dataStore.set([TANK_KEY], results);
    }

    async loadTank() {
        const tank = await this.client.getTank(new URLSearchParams(window.location.search).get('id'));
        const nameField = document.getElementById('tank-name');
        nameField.value = tank.name;
        this.dataStore.set([TANK_KEY], tank);
    }

    /**
     * Uses the client to perform the search, 
     * then updates the datastore with the criteria and results.
     * 
     */
    async search() {

        const tankId = new URLSearchParams(window.location.search).get('id');
        const results = await this.logClient.getLogsForTank(tankId);

        this.dataStore.set([SEARCH_RESULTS_KEY], results);


    }

    /**
     * Pulls search results from the datastore and displays them on the html page.
     */
    async displaySearchResults() {
        const searchResultsContainer = document.getElementById('search-results-container');

        searchResultsContainer.classList.remove('hidden');
        this.getHTMLForSearchResults();

    }

    /**
     * Create appropriate HTML for displaying searchResults on the page.
     * @param searchResults An array of playlists objects to be displayed on the page.
     * @returns A string of HTML suitable for being dropped on the page.
     */
    getHTMLForSearchResults() {
        const searchResults = this.dataStore.get(SEARCH_RESULTS_KEY);

        if (!searchResults) {
            var table = document.getElementById("log-table");
            var oldTableBody = table.getElementsByTagName('tbody')[0];
            var newTableBody = document.createElement('tbody');
            oldTableBody.parentNode.replaceChild(newTableBody, oldTableBody);
            return;
        }
        var preloads = document.getElementsByClassName('preload');
        for (var i = 0; i < preloads.length; i++) {
            preloads[i].hidden = false;
        }

        var table = document.getElementById("log-table");
        var oldTableBody = table.getElementsByTagName('tbody')[0];
        var newTableBody = document.createElement('tbody');


        for (const log of searchResults) {
            var row = newTableBody.insertRow();
            var cell1 = row.insertCell(0);
            var cell2 = row.insertCell(1);
            var cell3 = row.insertCell(2);

            cell1.innerHTML = '<a href="logDetails.html?id=' + log.logId 
            + '&tankId=' +  new URLSearchParams(window.location.search).get('id') + "\">" + log.flavor + '</a>';
            cell2.innerHTML = log.logDate;

            var deleteButton = document.createElement('button');
            deleteButton.textContent = 'Delete';
            deleteButton.className = 'deletebutton';
            deleteButton.setAttribute('data-log-id', log.logId);
            deleteButton.addEventListener('click', (event) => {
                var result = confirm("Confirm Delete log?");
                if (result) {
                    this.deleteLog(log.logId);
                }
                
            });

            cell3.appendChild(deleteButton);
        }

        oldTableBody.parentNode.replaceChild(newTableBody, oldTableBody);
    }

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const landingPage = new TankDetails();
    landingPage.mount();
};

window.addEventListener('DOMContentLoaded', main);