'use client' // next.js - this component is client-side only (state and events which require browser)

import { useEffect, useState } from 'react' // react hook to track/update values in UI (form and inputs)
import { Item } from '../types/Item'

export default function ItemList() {
    const [items, setItems] = useState<Item[]>([])
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState<string | null>(null)
    // edit states
    const [editingItemId, setEditingItemId] = useState<number | null>(null)
    const [editedName, setEditedName] = useState('')
    const [editedDescription, setEditedDescription] = useState('')

    useEffect(() => {
        const fetchItems = async () => {
            try {
                const res = await fetch('http://localhost:8080/api/items')
                if (!res.ok) throw new Error('Failed to fetch items')
                const data = await res.json()
                setItems(data)
            } catch (err) {
                setError((err as Error).message)
            } finally {
                setLoading(false)
            }
        }

        fetchItems()
    }, [])

    const handleDelete = async (id: number) => {
        try {
            const res = await fetch(`http://localhost:8080/api/items/${id}`, {
                method: 'DELETE',
            })
            if (!res.ok) throw new Error('Failed to delete item')
            setItems((prev) => prev.filter((item) => item.id !== id))
        } catch (err) {
            console.error('Error deleting item:', err)
        }
    }

    const handleEdit = (item: Item) => {
        setEditingItemId(item.id)
        setEditedName(item.name)
        setEditedDescription(item.description)
        console.log('Edit clicked for item:', item)
    }

    const handleSaveEdit = async (id: number) => {
        try {
            const res = await fetch(`http://localhost:8080/api/items/${id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    name: editedName,
                    description: editedDescription,
                }),
            })

            if (!res.ok) throw new Error('Failed to update item')

            const updatedItem = await res.json()
            setItems((prev) =>
                prev.map((item) => (item.id === id ? updatedItem : item))
            )

            setEditingItemId(null) // Exit edit mode
        } catch (err) {
            console.error('Error updating item:', err)
        }
    }

    if (loading) return <p>Loading...</p>
    if (error) return <p>Error: {error}</p>

    return (
        <div className="space-y-4">
            {items.map((item) => (
                <div key={item.id} className="border p-4 rounded shadow-sm">
                    {editingItemId === item.id ? (
                        <>
                            <input
                                type="text"
                                value={editedName}
                                onChange={(e) => setEditedName(e.target.value)}
                                className="border p-2 mb-2 w-full rounded"
                                placeholder="Item name"
                            />
                            <input
                                type="text"
                                value={editedDescription}
                                onChange={(e) =>
                                    setEditedDescription(e.target.value)
                                }
                                className="border p-2 mb-2 w-full rounded"
                                placeholder="Item description"
                            />
                        </>
                    ) : (
                        <>
                            <h2 className="text-xl font-bold">{item.name}</h2>
                            <p className="text-gray-700">{item.description}</p>
                        </>
                    )}

                    <div className="mt-2 space-x-2">
                        {editingItemId === item.id ? (
                            <>
                                <button
                                    className="px-4 py-1 text-white bg-green-500 rounded hover:bg-green-600"
                                    onClick={() => handleSaveEdit(item.id)}
                                >
                                    Save
                                </button>
                                <button
                                    className="px-4 py-1 text-white bg-gray-500 rounded hover:bg-gray-600"
                                    onClick={() => setEditingItemId(null)}
                                >
                                    Cancel
                                </button>
                            </>
                        ) : (
                            <>
                                <button
                                    className="px-4 py-1 text-white bg-blue-500 rounded hover:bg-blue-600"
                                    onClick={() => handleEdit(item)}
                                >
                                    Edit
                                </button>
                                <button
                                    className="px-4 py-1 text-white bg-red-500 rounded hover:bg-red-600"
                                    onClick={() => handleDelete(item.id)}
                                >
                                    Delete
                                </button>
                            </>
                        )}
                    </div>
                </div>
            ))}
        </div>
    )
}
