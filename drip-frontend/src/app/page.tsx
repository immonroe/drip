'use client'

import { useState } from 'react'
import ItemList from '../components/ItemList'
import AddItemForm from '../components/AddItemForm'

export default function Home() {
    // used to re-render list when new item is added
    const [refreshKey, setRefreshKey] = useState(0)

    const handleItemAdded = () => {
        setRefreshKey((prev) => prev + 1)
    }
    return (
        <main className="p-8 max-w-2xl mx-auto">
            <h1 className="text-3xl font-bold mb-6">All Items</h1>
            <AddItemForm onItemAdded={handleItemAdded} />
            {/* refresh key below is telling react that when a new item is added to mount ItemList again */}
            <ItemList key={refreshKey} />
        </main>
    )
}
