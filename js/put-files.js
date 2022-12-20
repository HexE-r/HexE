import process from 'process'
import minimist from 'minimist'
import { Web3Storage, getFilesFromPath } from 'web3.storage'

async function main () {
  const args = minimist(process.argv.slice(1))
  const token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJkaWQ6ZXRocjoweDJkNzU0NEFBNmZlQTE3NkFmRTUxNDVDMjMwMjc2MTFFMEVlMDkxNDAiLCJpc3MiOiJ3ZWIzLXN0b3JhZ2UiLCJpYXQiOjE2NjU4MjczMDEyNTAsIm5hbWUiOiJIZXhFIn0.t8VcYSUSOAr3PQ1ib1zlE9-EkHan3uXNIBptyGbWtYA"

  if (args._.length < 1) {
    return console.error('Please supply the path to a file or directory')
  }

  const storage = new Web3Storage({ token })
  const files = []

  for (const path of args._) {
    const pathFiles = await getFilesFromPath(path)
    files.push(...pathFiles)
  }

  console.log(`Uploading ${files.length} files`)
  const cid = await storage.put(files)
  console.log('Content added with CID:', cid)
}

main()
