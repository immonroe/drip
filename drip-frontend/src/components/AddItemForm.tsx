'use client' // next.js - this component is client-side only (state and events which require browser)

import { useState } from 'react' // react hook to track/update values in UI (form and inputs)

// state setup
export default function AddItemForm({
    onItemAdded,
}: {
    onItemAdded: () => void
}) {
    const [name, setName] = useState('')
    const [description, setDescription] = useState('')
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState<string | null>(null)

    // Prevents default HTML page reload when user hit "add item" button
    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault()
        setLoading(true)
        setError(null)

        try {
            const res = await fetch('http://localhost:8080/api/items', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ name, description }),
            })

            if (!res.ok) throw new Error('Failed to create item')

            // clear inputs on successful post
            setName('')
            setDescription('')
            onItemAdded() // notify the parent to refresh the list
        } catch (err) {
            setError((err as Error).message)
        } finally {
            setLoading(false)
        }
    }

    return (
        <form onSubmit={handleSubmit} className="space-y-4 mb-8">
            <div>
                <label className="block font-medium">Name</label>
                <input
                    type="text"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    className="w-full border rounded px-3 py-2"
                    required
                />
            </div>
            <div>
                <label className="block font-medium">Description</label>
                <textarea
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                    className="w-full border rounded px-3 py-2"
                    required
                />
            </div>
            <button
                type="submit"
                className="bg-black text-white px-4 py-2 rounded"
                disabled={loading}
            >
                {loading ? 'Adding...' : 'Add Item'}
            </button>
            {error && <p className="text-red-500">{error}</p>}
        </form>
    )
}
