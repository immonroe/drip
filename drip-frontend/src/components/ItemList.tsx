// src/components/ItemList.tsx
"use client"

import { useEffect, useState } from "react"
import { Item } from "../types/Item"

export default function ItemList() {
  const [items, setItems] = useState<Item[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    const fetchItems = async () => {
      try {
        const res = await fetch("http://localhost:8080/api/items")
        if (!res.ok) throw new Error("Failed to fetch items")
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

  if (loading) return <p>Loading...</p>
  if (error) return <p>Error: {error}</p>

  return (
    <div className="space-y-4">
      {items.map((item) => (
        <div key={item.id} className="border p-4 rounded shadow-sm">
          <h2 className="text-xl font-bold">{item.name}</h2>
          <p className="text-gray-700">{item.description}</p>
        </div>
      ))}
    </div>
  )
}
