import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import TankClient from '../api/tankClient';

/*
The code below this comment is equivalent to...
const EMPTY_DATASTORE_STATE = {
    'search-criteria': '',
    'search-results': [],
};

...but uses the "KEY" constants instead of "magic strings".
The "KEY" constants will be reused a few times below.
*/

const SEARCH_CRITERIA_KEY = 'search-criteria';
const SEARCH_RESULTS_KEY = 'search-results';
const EMPTY_DATASTORE_STATE = {
    [SEARCH_CRITERIA_KEY]: '',
    [SEARCH_RESULTS_KEY]: [],
};


/**
 * Logic needed for the landing page of the website.
 */
class LandingPage extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount', 'search', 'displaySearchResults', 'getHTMLForSearchResults', 'createTank', 'deleteTank'], this);

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
        this.search();
        document.getElementById('create-tank').addEventListener('click', this.createTank)
    }

    async createTank(evt) {
        const name = document.getElementById('tank-name').value;
        await this.client.createTank(name).then(response => {
        }).catch(e => {
            console.log(e);
        });;
        this.search();
    }

    async deleteTank(evt) {
        await this.client.deleteTank(evt).then(response => {
        }).catch(e => {
            console.log(e);
        });;
        this.search();
    }

    /**
     * Uses the client to perform the search, 
     * then updates the datastore with the criteria and results.
     * @param evt The "event" object representing the user-initiated event that triggered this method.
     */
    async search(evt) {
        const results = await this.client.getTanks();

        this.dataStore.setState({
            [SEARCH_CRITERIA_KEY]: SEARCH_CRITERIA_KEY,
            [SEARCH_RESULTS_KEY]: results,
        });
    }

    /**
     * Pulls search results from the datastore and displays them on the html page.
     */
    async displaySearchResults() {
        const searchCriteria = this.dataStore.get(SEARCH_CRITERIA_KEY);
        const searchResults = this.dataStore.get(SEARCH_RESULTS_KEY);

        const searchResultsContainer = document.getElementById('search-results-container');
        const searchCriteriaDisplay = document.getElementById('search-criteria-display');
        const searchResultsDisplay = document.getElementById('search-results-display');

        if (searchCriteria === '') {
            searchResultsContainer.classList.add('hidden');
            searchCriteriaDisplay.innerHTML = '';
            searchResultsDisplay.innerHTML = '';
        } else {
            searchResultsContainer.classList.remove('hidden');
            this.getHTMLForSearchResults(searchResults);

            const table = document.getElementsByTagName("table")[0];

            // table.addEventListener('click', (e) => {
            //     console.log(`${e.target.dataset.characterId} clicked`);
            //     console.log(`${e.target.parentNode.dataset.rowId} row`);
            // })
        }
    }

    /**
     * Create appropriate HTML for displaying searchResults on the page.
     * @param searchResults An array of playlists objects to be displayed on the page.
     * @returns A string of HTML suitable for being dropped on the page.
     */
    getHTMLForSearchResults(searchResults) {
        if (!searchResults) {
            var table = document.getElementById("tank-table");
            var oldTableBody = table.getElementsByTagName('tbody')[0];
            var newTableBody = document.createElement('tbody');
            oldTableBody.parentNode.replaceChild(newTableBody, oldTableBody);
            return;
        }
        var preloads = document.getElementsByClassName('preload');
        for (var i = 0; i < preloads.length; i++) {
            preloads[i].hidden = false;
        }

        var table = document.getElementById("tank-table");
        var oldTableBody = table.getElementsByTagName('tbody')[0];
        var newTableBody = document.createElement('tbody');

        for (const tank of searchResults) {
            var row = newTableBody.insertRow();
            var cell1 = row.insertCell(0);
            var cell2 = row.insertCell(1);
            var cell3 = row.insertCell(2);
            var cell4 = row.insertCell(3);


            cell1.innerHTML = tank.name;

            cell2.innerHTML = '<a href="tankDetails.html?id=' + tank.tankId + "\" class=\"greenbutton\" id=\"fish-button\">" + "Logs" + '</a>';

            cell3.innerHTML = '<a href="fishList.html?id=' + tank.tankId + "\" class=\"button\" id=\"fish-button\">" + "Fish" + '</a>';



            var deleteButton = document.createElement('button');
            deleteButton.textContent = 'Delete';
            deleteButton.className = 'deletebutton';
            deleteButton.setAttribute('data-tank-id', tank.tankId);
            deleteButton.addEventListener('click', (event) => {
                var result = confirm("This will delete the tank and all of its logs. Are you sure?");
                if (result) {
                    var tankId = event.target.getAttribute('data-tank-id');
                    this.deleteTank(tankId);
                }
            });

            cell4.appendChild(deleteButton);
        }

        oldTableBody.parentNode.replaceChild(newTableBody, oldTableBody);
    }

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const landingPage = new LandingPage();
    landingPage.mount();
};

window.addEventListener('DOMContentLoaded', main);