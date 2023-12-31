import React, { useEffect, useState } from 'react';
import axios from 'axios';

function Hello() {
    const [hello, setHello] = useState('')

    useEffect(() => {
        axios.get('/api/hello')
            .then(response => setHello(response.data))
            .catch(error => console.log(error))
    }, []);
    return (
        <div className="App">
            백엔드 데이터 : {hello}
        </div>
    )
}

export default Hello;