import React, { useState, useEffect } from 'react';

const CitySearch = ({ onCitySelect }) => {
    const [city, setCity] = useState('');
    const [error, setError] = useState(null); 
    const [validCities, setValidCities] = useState([]);

    useEffect(() => {
        const fetchCities = async () => {
            try {
                //const response = await fetch('http://54.146.212.23:8080/api/v1/city');  
                const response = await fetch('http://localhost:8080/api/v1/city');  
                const data = await response.json();
                console.log("Fetched Cities: ", data);  
                setValidCities(data); 
            } catch (err) {
                setError('Failed to fetch cities');
            }
        };

        fetchCities();
    }, []);

    const handleCityChange = (e) => {
        setCity(e.target.value);
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        const foundCity = validCities.find(c => c.name.toLowerCase() === city.toLowerCase());

        if (foundCity) {
            setError(null); 
            onCitySelect(foundCity.name);  
        } else {
            setError('City not found');  
        }
    };

    return (
        <div>
            <form onSubmit={handleSubmit}>
                <label>
                    Enter City:
                    <input type="text" value={city} onChange={handleCityChange} />
                </label>
                <button type="submit">Search</button>
            </form>
            {error && <p>{error}</p>}  
        </div>
    );
};

export default CitySearch;
