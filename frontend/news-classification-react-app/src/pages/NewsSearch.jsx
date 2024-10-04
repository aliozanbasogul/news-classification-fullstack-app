import React, { useState } from 'react';
import axios from 'axios';
import CitySearch from '../components/CitySearch';  
import '../styles/NewsSearchCss.css';  

const NewsSearch = () => {
    const [city, setCity] = useState('');
    const [news, setNews] = useState({ global: [], local: [] });
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);  

    const handleCitySelect = (selectedCity) => {
        setCity(selectedCity);  
        fetchNews(selectedCity);  
    };

    const fetchNews = async (city) => {
        setLoading(true);  
        setNews({ global: [], local: [] });  

        try {
            //const response = await axios.post('http://54.146.212.23:8080/api/v1/classify-news', { city }); 
            const response = await axios.post('http://localhost:8080/api/v1/classify-news', { city }); 
            setNews(response.data);
            setError(null);
        } catch (err) {
            setError('Failed to fetch news');
            console.error(err);
        }
        
        setLoading(false); 
    };

    return (
        <div>
            <CitySearch onCitySelect={handleCitySelect} />

            {error && <p>{error}</p>}
            {loading && <p>Loading...</p>}  

            <div className="news-container">
                <div className="news-column">
                    <h1>Global News</h1>
                    <ul>
                        {news.global.map((newsItem, index) => (
                            <li key={index}>
                                <h3>{newsItem.title}</h3>
                                <p>{newsItem.summary}</p>
                            </li>
                        ))}
                    </ul>
                </div>

                <div className="news-column">
                    <h1>Local News for: {city}</h1>
                    <ul>
                        {news.local.map((newsItem, index) => (
                            <li key={index}>
                                <h3>{newsItem.title}</h3>
                                <p>{newsItem.summary}</p>
                            </li>
                        ))}
                    </ul>
                </div>
            </div>
        </div>
    );
};

export default NewsSearch;
