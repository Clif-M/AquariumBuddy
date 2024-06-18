import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import TankClient from '../api/tankClient';
import FishClient from '../api/fishClient';
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
const FISH_LOADED = 'fish-key';

const SEARCH_CRITERIA_KEY = 'search-criteria';
const SEARCH_RESULTS_KEY = 'search-results';
const EMPTY_DATASTORE_STATE = {
    [SEARCH_CRITERIA_KEY]: '',
    [SEARCH_RESULTS_KEY]: [],
    [FISH_LOADED]: [],
};


/**
 * Fishic needed for the landing page of the website.
 */
class FishList extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount', 'displaySearchResults', 'getHTMLForSearchResults', 'updateTank', 'createFish', 'deleteFish', 'loadTank'], this);

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
        this.fishClient = new FishClient();
        this.loadTank();
        document.getElementById('update-name').addEventListener('click', this.updateTank)
        document.getElementById('create-fish-button').addEventListener('click', this.createFish)
        // document.getElementById('search-fish-button').addEventListener('click', this.getFishByType)


    }

    async createFish() {
        const name = document.getElementById('fish-name').value;
        const imageUrl = document.getElementById('fish-url').value;
        const species = document.getElementById('fish-species').value;
    
        const fish = await this.fishClient.createFish(name, imageUrl, species);
        var list = this.dataStore.get(FISH_LOADED);
        list.push(fish);
        this.dataStore.set([FISH_LOADED], list);
        var tank = this.dataStore.get(TANK_KEY);
        tank.fishList = list;
        this.dataStore.set([TANK_KEY], tank);
        this.updateTank();
    }


    async deleteFish(fishId) {
       
        var fishList = this.dataStore.get(FISH_LOADED);
       

        fishList = fishList.filter(fish => fish.fishId !== fishId);
      

        var tank = this.dataStore.get(TANK_KEY);
        tank.fishList = fishList;
      
       
        this.dataStore.set([TANK_KEY], tank);
        this.dataStore.set([FISH_LOADED], tank.fishList);
        
        
        await this.updateTank();
        
        await this.fishClient.deleteFish(fishId);

        this.getHTMLForSearchResults();
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

        var list = new Array();
        if (tank.fishList) {
        for (const fish of tank.fishList) {
            const loadedFish = await this.fishClient.getSingleFish(fish.fishId);
            list.push(loadedFish);
        }
    }
        this.dataStore.set([FISH_LOADED], list);

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
        const searchResults = this.dataStore.get(FISH_LOADED);

        if (!searchResults) {
            var table = document.getElementById("fish-table");
            var oldTableBody = table.getElementsByTagName('tbody')[0];
            var newTableBody = document.createElement('tbody');
            oldTableBody.parentNode.replaceChild(newTableBody, oldTableBody);
            return;
        }
        var preloads = document.getElementsByClassName('preload');
        for (var i = 0; i < preloads.length; i++) {
            preloads[i].hidden = false;
        }

        var table = document.getElementById("fish-table");
        var oldTableBody = table.getElementsByTagName('tbody')[0];
        var newTableBody = document.createElement('tbody');


        for (const fish of searchResults) {
            if (!fish) {continue;}
            var row = newTableBody.insertRow();
            var cell1 = row.insertCell(0);
            var cell2 = row.insertCell(1);
            var cell3 = row.insertCell(2);

            cell1.innerHTML ='<a href="fishDetails.html?id=' + fish.fishId + '&tankId=' +  new URLSearchParams(window.location.search).get('id') + "\">" + '<figure><img src="' + fish.imageUrl + '"' +  "alt='No Image Available.'/>" +
            "<figcaption>" + fish.name + "</figcaption>" + "</figure>" + '</a>';

            cell2.innerHTML = fish.species;

            var deleteButton = document.createElement('button');
            deleteButton.textContent = 'Delete';
            deleteButton.className = 'deletebutton';
            deleteButton.setAttribute('data-fish-id', fish.fishId);
            deleteButton.addEventListener('click', (event) => {
                var result = confirm("Confirm Delete fish?");
                if (result) {
                    this.deleteFish(fish.fishId);
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
    const landingPage = new FishList();
    landingPage.mount();
};

window.addEventListener('DOMContentLoaded', main);