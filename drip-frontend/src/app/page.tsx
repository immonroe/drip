import ItemList from "../components/ItemList"

export default function Home() {
  return (
    <main className="p-8">
      <h1 className="text-3xl font-bold mb-6">All Items</h1>
      <ItemList />
    </main>
  )
}
