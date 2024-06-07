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

        this.bindClassMethods(['mount', 'search', 'displaySearchResults', 'getHTMLForSearchResults', 'createTank'], this);

        // Create a enw datastore with an initial "empty" state.
        this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
        this.header = new Header(this.dataStore);
        this.dataStore.addChangeListener(this.displaySearchResults);

        console.log("landingPage constructor");
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
        this.client.createTank(name).then(response => {
        }).catch(e => {
            console.log(e);
        });;
    }

    /**
     * Uses the client to perform the search, 
     * then updates the datastore with the criteria and results.
     * @param evt The "event" object representing the user-initiated event that triggered this method.
     */
    async search(evt) {
        console.log("search function running");
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
            searchResultsDisplay.innerHTML = this.getHTMLForSearchResults(searchResults);

            const table = document.getElementsByTagName("table")[0];

            table.addEventListener('click', (e) => {
                console.log(`${e.target.dataset.characterId} clicked`);
                console.log(`${e.target.parentNode.dataset.rowId} row`);
            })
        }
    }

    /**
     * Create appropriate HTML for displaying searchResults on the page.
     * @param searchResults An array of playlists objects to be displayed on the page.
     * @returns A string of HTML suitable for being dropped on the page.
     */
    getHTMLForSearchResults(searchResults) {
        if (searchResults.length === 0) {
            return '<h4>No results found</h4>';
        }

        let html = '<table class="table"><tr><th>Name</th><th>Details</th></tr>';
        for (const res of searchResults) {
            html += `
            <tr>
                <td>
                    <a>${res.name}</a>
                    <td><a href="tankDetails.html?id=${res.tankId}" class="button centered" id="details">Details</a></td>
                    <td></td>
                    <td><a href="#" class ="deletebutton centered" id="delete-tank">Delete</a></td>
                </td>
            </tr>`;
        }
        html += '</table>';

        return html;
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